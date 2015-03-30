package com.arborsoft.librarian.repository;

import com.arborsoft.librarian.constant.Relationship;
import com.arborsoft.librarian.converter.NodeConverter;
import com.arborsoft.librarian.model.Author;
import com.arborsoft.librarian.model.Book;
import com.arborsoft.librarian.model.Review;
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

import java.util.Date;
import java.util.Map;

import static com.arborsoft.librarian.util.CollectionUtils.map;
import static com.arborsoft.librarian.util.Pair.pair;
import static org.neo4j.cypherdsl.CypherQuery.*;

@Service
public class BookRepository {
    private static final Logger LOG = LoggerFactory.getLogger(BookRepository.class);
    private static final NodeConverter<Book> CONVERTER_BOOK = new NodeConverter<>();

    @Autowired private Neo4jTemplate template;

    public Book create(Book book) throws Exception {
        Assert.notNull(book, "Book is null");
        Assert.isNull(book.getId(), "Book.id is not null");

        return this.template.save(book);
    }

    public Book findBookById(long id) throws Exception {
        return this.template.findById(Book.class, id);
    }

    public Book findBookByIsbn(String isbn) throws Exception {
        Assert.notNull(isbn, "Book.isbn is null");

        Execute query = CypherQuery
                .match(node("book").label("Book").values(value("isbn", isbn)))
                .returns(identifier("book"));

        Map<String, Object> param = map();

        return this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }).singleOrNull();
    }

    public FluentIterable<Book> findBooksByTitle(String title) throws Exception {
        Assert.notNull(title, "Book.title is null");

        Execute query = CypherQuery
                .match(node("book").label("Book"))
                .where(identifier("book").property("title").eq(param("title")))
                .returns(identifier("book"));

        Map<String, Object> param = map(
                pair("title", (Object) title));

        return FluentIterable.from(this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }));
    }

    public FluentIterable<Book> findBooksByAuthor(Author author) throws Exception {
        Assert.notNull(author, "Author is null");

        Execute query = CypherQuery
                .start(nodesById("author", author.getId()))
                .match(node("author").out(Relationship.WRITES_BOOK).node("book").label("Book"))
                .returns(identifier("book"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }));
    }

    public Book findBookByReview(Review review) throws Exception {
        Assert.notNull(review, "Review is null");

        Execute query = CypherQuery
                .start(nodesById("review", review.getId()))
                .match(node("review").out(Relationship.OF_BOOK).node("book").label("Book"))
                .returns(identifier("book"));

        Map<String, Object> param = map();

        return this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }).singleOrNull();
    }

    public FluentIterable<Book> findBooksWrittenBefore(Date date) throws Exception {
        Assert.notNull(date, "Date is null");

        Execute query = CypherQuery
                .match(node("book").label("Book"))
                .where(
                        and(
                                has(identifier("book").property("writtenDate")),
                                identifier("book").property("writtenDate").lt(date.getTime())
                        )
                )
                .returns(identifier("book"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }));
    }

    public FluentIterable<Book> findBooksWrittenAfter(Date date) throws Exception {
        Assert.notNull(date, "Date is null");

        Execute query = CypherQuery
                .match(node("book").label("Book"))
                .where(
                        and(
                                has(identifier("book").property("writtenDate")),
                                identifier("book").property("writtenDate").gt(date.getTime())
                        )
                )
                .returns(identifier("book"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }));
    }

    public FluentIterable<Book> findBooksPublishedBefore(Date date) throws Exception {
        Assert.notNull(date, "Date is null");

        Execute query = CypherQuery
                .match(node("book").label("Book"))
                .where(
                        and(
                                has(identifier("book").property("publishedDate")),
                                identifier("book").property("publishedDate").lt(date.getTime())
                        )
                )
                .returns(identifier("book"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }));
    }

    public FluentIterable<Book> findBooksPublishedAfter(Date date) throws Exception {
        Assert.notNull(date, "Date is null");

        Execute query = CypherQuery
                .match(node("book").label("Book"))
                .where(
                        and(
                                has(identifier("book").property("publishedDate")),
                                identifier("book").property("publishedDate").gt(date.getTime())
                        )
                )
                .returns(identifier("book"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Book.class, new ResultConverter<Map<String, Object>, Book>() {
            @Override
            public Book convert(Map<String, Object> map, Class<Book> aClass) {
                return CONVERTER_BOOK.map((Node) map.get("book"));
            }
        }));
    }

    public void setAuthorToBook(Book book, Author author) throws Exception {
        Assert.notNull(book, "Book is null");
        Assert.notNull(author, "Author is null");

        Execute query = CypherQuery
                .start(nodesById("book", book.getId()), nodesById("author", author.getId()))
                .createUnique(node("author").out(Relationship.WRITES_BOOK).node("book"))
                .createUnique(node("book").out(Relationship.WRITTEN_BY).node("author"));

        Map<String, Object> param = map();

        this.template.query(query, param);
    }
}
