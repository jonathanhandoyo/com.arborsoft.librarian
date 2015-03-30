package com.arborsoft.librarian.annotation;

import com.arborsoft.librarian.constant.Label;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Labeled {
    Label[] value();
}
