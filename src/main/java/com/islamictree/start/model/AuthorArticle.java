package com.islamictree.start.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author_article")
public class AuthorArticle {

    @Column("author_id")
    private Long authorId;

    @Column("article_id")
    private Long articleId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        AuthorArticle that = (AuthorArticle) o;
        return authorId.equals(that.authorId) && articleId.equals(that.articleId);
    }

    @Override
    public int hashCode() {
        int result = authorId.hashCode();
        result = 31 * result + articleId.hashCode();
        return result;
    }
}
