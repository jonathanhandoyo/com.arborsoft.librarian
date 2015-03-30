package com.arborsoft.librarian.model;

import com.arborsoft.librarian.annotation.Labeled;
import com.arborsoft.librarian.annotation.RelatedTo;
import com.arborsoft.librarian.constant.Label;
import com.arborsoft.librarian.constant.Relationship;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Labeled({Label.Review})
public class Review extends BaseNode {
    protected int score;
    protected String remark;

    @RelatedTo(Relationship.OF_BOOK)
    protected Book book;

    @RelatedTo(Relationship.REVIEWED_BY)
    protected Reviewer reviewer;
}
