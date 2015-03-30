package com.arborsoft.librarian.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseNode {
    protected String code;
    protected String name;
    protected String password;
    protected Role[] roles;

    public enum Role {
        ADMIN,
        USER,
        ;
    }
}
