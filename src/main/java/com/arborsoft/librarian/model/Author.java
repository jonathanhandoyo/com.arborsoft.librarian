package com.arborsoft.librarian.model;

import com.arborsoft.librarian.annotation.Labeled;
import com.arborsoft.librarian.annotation.RelatedTo;
import com.arborsoft.librarian.constant.Label;
import com.arborsoft.librarian.constant.Relationship;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Labeled({Label.Author})
public class Author extends BaseNode {
    protected String name;

    @RelatedTo(Relationship.WRITES_BOOK)
    protected Set<Book> books;
}
