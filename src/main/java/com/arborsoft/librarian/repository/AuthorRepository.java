package com.arborsoft.librarian.repository;

import com.arborsoft.librarian.constant.Relationship;
import com.arborsoft.librarian.converter.NodeConverter;
import com.arborsoft.librarian.model.Author;
import com.arborsoft.librarian.model.Book;
import com.google.common.collect.FluentIterable;
import org.neo4j.cypherdsl.CypherQuery;
import org.neo4j.cypherdsl.grammar.Execute;
import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.util.ResultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

import static com.arborsoft.librarian.util.CollectionUtils.map;
import static org.neo4j.cypherdsl.CypherQuery.*;

@Service
public class AuthorRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorRepository.class);
    private static final NodeConverter<Author> CONVERTER_AUTHOR = new NodeConverter<>();

    @Autowired private Neo4jTemplate template;

    public Author create(Author author) throws Exception {
        Assert.notNull(author, "Author is null");
        Assert.isNull(author.getId(), "Author.id is not null");

        return this.template.save(author);
    }

    public Author findAuthorById(long id) throws Exception {
        return this.template.findById(Author.class, id);
    }

    public Author findAuthorByName(String name) throws Exception {
        Assert.notNull(name, "Author.name is null");

        Execute query = CypherQuery
                .match(node("author").label("Author").values(value("name", name)))
                .returns(identifier("author"));

        Map<String, Object> param = map();

        return this.template.query(query, param).to(Author.class, new ResultConverter<Map<String, Object>, Author>() {
            @Override
            public Author convert(Map<String, Object> map, Class<Author> aClass) {
                return CONVERTER_AUTHOR.map((Node) map.get("author"));
            }
        }).singleOrNull();
    }

    public FluentIterable<Author> findAuthorsByBook(Book book) throws Exception {
        Assert.notNull(book, "Book is null");

        Execute query = CypherQuery
                .start(nodesById("book", book.getId()))
                .match(node("book").out(Relationship.WRITTEN_BY).node("author").label("Author"))
                .returns(identifier("author"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Author.class, new ResultConverter<Map<String, Object>, Author>() {
            @Override
            public Author convert(Map<String, Object> map, Class<Author> aClass) {
                return CONVERTER_AUTHOR.map((Node) map.get("author"));
            }
        }));
    }
}
