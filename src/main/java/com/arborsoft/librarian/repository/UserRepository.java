package com.arborsoft.librarian.repository;

import com.arborsoft.librarian.converter.NodeConverter;
import com.arborsoft.librarian.model.Book;
import com.arborsoft.librarian.model.User;
import com.arborsoft.librarian.util.Pair;
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
import static com.arborsoft.librarian.util.Pair.pair;
import static org.neo4j.cypherdsl.CypherQuery.*;

@Service
public class UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private static final NodeConverter<User> CONVERTER_USER = new NodeConverter<>();

    @Autowired
    private Neo4jTemplate template;

    public User create(User user) throws Exception {
        Assert.notNull(user, "User is null");
        Assert.isNull(user.getId(), "User.id is not null");

        return this.template.save(user);
    }

    public User findUserById(long id) throws Exception {
        return this.template.findById(User.class, id);
    }

    public User findUserByCode(String code) throws Exception {
        Assert.notNull(code, "Author.code is null");

        Execute query = CypherQuery
                .match(node("user").label("User").values(value("code", code)))
                .returns(identifier("user"));

        Map<String, Object> param = map();

        return this.template.query(query, param).to(User.class, new ResultConverter<Map<String, Object>, User>() {
            @Override
            public User convert(Map<String, Object> map, Class<User> aClass) {
                return CONVERTER_USER.map((Node) map.get("user"));
            }
        }).singleOrNull();
    }

    public User findUserByName(String name) throws Exception {
        Assert.notNull(name, "Author.name is null");

        Execute query = CypherQuery
                .match(node("user").label("User").values(value("name", name)))
                .returns(identifier("user"));

        Map<String, Object> param = map();

        return this.template.query(query, param).to(User.class, new ResultConverter<Map<String, Object>, User>() {
            @Override
            public User convert(Map<String, Object> map, Class<User> aClass) {
                return CONVERTER_USER.map((Node) map.get("user"));
            }
        }).singleOrNull();
    }

    public FluentIterable<User> findUsersByRole(User.Role role) throws Exception {
        Assert.notNull(role, "Role is null");

        Execute query = CypherQuery
                .match(node("user").label("User"))
                .where(param("role").in(identifier("user").property("role")))
                .returns(identifier("user"));

        Map<String, Object> param = map(pair("role", (Object) role));

        return FluentIterable.from(this.template.query(query, param).to(User.class, new ResultConverter<Map<String, Object>, User>() {
            @Override
            public User convert(Map<String, Object> map, Class<User> aClass) {
                return CONVERTER_USER.map((Node) map.get("user"));
            }
        }));
    }

    public User authenticate(String code, String password) throws Exception {
        Assert.notNull(code, "Author.code is null");

        Execute query = CypherQuery
                .match(node("user").label("User")
                        .values(
                                value("code", code),
                                value("password", password)
                        )
                )
                .returns(identifier("user"));

        Map<String, Object> param = map();

        return this.template.query(query, param).to(User.class, new ResultConverter<Map<String, Object>, User>() {
            @Override
            public User convert(Map<String, Object> map, Class<User> aClass) {
                return CONVERTER_USER.map((Node) map.get("user"));
            }
        }).singleOrNull();
    }
}
