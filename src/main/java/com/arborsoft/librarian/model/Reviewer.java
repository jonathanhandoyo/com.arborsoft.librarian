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
@Labeled({Label.Reviewer})
public class Reviewer extends BaseNode {
    protected String firstName;
    protected String lastName;

    @RelatedTo(Relationship.WRITES_REVIEW)
    protected Set<Review> reviews;
}
