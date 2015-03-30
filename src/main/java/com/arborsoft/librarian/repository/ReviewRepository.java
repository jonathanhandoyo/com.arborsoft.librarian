package com.arborsoft.librarian.repository;

import com.arborsoft.librarian.constant.Relationship;
import com.arborsoft.librarian.converter.NodeConverter;
import com.arborsoft.librarian.model.*;
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
import static org.neo4j.cypherdsl.CypherQuery.identifier;
import static org.neo4j.cypherdsl.CypherQuery.node;
import static org.neo4j.cypherdsl.CypherQuery.nodesById;

@Service
public class ReviewRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ReviewRepository.class);
    private static final NodeConverter<Review> CONVERTER_REVIEW = new NodeConverter<>();

    @Autowired
    private Neo4jTemplate template;

    public Review create(Review review) throws Exception {
        Assert.notNull(review, "Review is null");
        Assert.isNull(review.getId(), "Review.id is not null");

        return this.template.save(review);
    }

    public Review findReviewById(long id) throws Exception {
        return this.template.findById(Review.class, id);
    }

    public FluentIterable<Review> findReviewsByBook(Book book) throws Exception {
        Assert.notNull(book, "Book is null");

        Execute query = CypherQuery
                .start(nodesById("book", book.getId()))
                .match(node("review").out(Relationship.OF_BOOK).node("book").label("Book"))
                .returns(identifier("review"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Review.class, new ResultConverter<Map<String, Object>, Review>() {
            @Override
            public Review convert(Map<String, Object> map, Class<Review> aClass) {
                return CONVERTER_REVIEW.map((Node) map.get("review"));
            }
        }));
    }

    public FluentIterable<Review> findReviewsByReviewer(Reviewer reviewer) throws Exception {
        Assert.notNull(reviewer, "Reviewer is null");

        Execute query = CypherQuery
                .start(nodesById("reviewer", reviewer.getId()))
                .match(node("review").out(Relationship.REVIEWED_BY).node("reviewer").label("Reviewer"))
                .returns(identifier("review"));

        Map<String, Object> param = map();

        return FluentIterable.from(this.template.query(query, param).to(Review.class, new ResultConverter<Map<String, Object>, Review>() {
            @Override
            public Review convert(Map<String, Object> map, Class<Review> aClass) {
                return CONVERTER_REVIEW.map((Node) map.get("review"));
            }
        }));
    }
}
