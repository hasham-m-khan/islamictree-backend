package com.islamictree.start.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author_occupation")
public class AuthorOccupation {

    @Column("author_id")
    private Long authorId;

    @Column("occupation_id")
    private Long occupationId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        AuthorOccupation that = (AuthorOccupation) o;
        return authorId.equals(that.authorId) && occupationId.equals(that.occupationId);
    }

    @Override
    public int hashCode() {
        int result = authorId.hashCode();
        result = 31 * result + occupationId.hashCode();
        return result;
    }
}
