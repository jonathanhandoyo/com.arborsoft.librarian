package com.arborsoft.librarian.constant;

public enum Relationship {
    HAS_REVIEW,     // (:Book) --> (:Review)
    OF_BOOK,        // (:Review) --> (:Book)
    REVIEWED_BY,    // (:Review) --> (:Reviewer)
    WRITES_BOOK,    // (:Author) --> (:Book)
    WRITES_REVIEW,  // (:Reviewer) --> (:Review)
    WRITTEN_BY,     // (:Book) --> (:Author)
    ;
}
