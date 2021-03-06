package com.arborsoft.librarian.annotation;

import com.arborsoft.librarian.constant.Relationship;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface RelatedTo {
    Relationship value();
}
