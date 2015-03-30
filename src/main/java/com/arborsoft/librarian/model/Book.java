package com.arborsoft.librarian.model;

import com.arborsoft.librarian.annotation.Labeled;
import com.arborsoft.librarian.annotation.RelatedTo;
import com.arborsoft.librarian.constant.Label;
import com.arborsoft.librarian.constant.Relationship;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Labeled({Label.Book})
public class Book extends BaseNode {
    protected String isbn;
    protected String title;
    protected Date publishedDate;
    protected Date writtenDate;
    protected String[] genres;

    @RelatedTo(Relationship.WRITTEN_BY)
    protected Author author;

    @RelatedTo(Relationship.HAS_REVIEW)
    protected Set<Review> reviews;
}
