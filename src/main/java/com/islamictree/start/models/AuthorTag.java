package com.islamictree.start.models;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author_tag")
public class AuthorTag {

    @Column("author_id")
    private Long authorId;

    @Column("tag_id")
    private Long tagId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        AuthorTag authorTag = (AuthorTag) o;
        return authorId.equals(authorTag.authorId) && tagId.equals(authorTag.tagId);
    }

    @Override
    public int hashCode() {
        int result = authorId.hashCode();
        result = 31 * result + tagId.hashCode();
        return result;
    }
}
