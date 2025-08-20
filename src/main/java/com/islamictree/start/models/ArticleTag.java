package com.islamictree.start.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_tag")
public class ArticleTag {

    @Column("article_id")
    private Long articleId;

    @Column("tag_id")
    private Long tagId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ArticleTag that = (ArticleTag) o;
        return articleId.equals(that.articleId) && tagId.equals(that.tagId);
    }

    @Override
    public int hashCode() {
        int result = articleId.hashCode();
        result = 31 * result + tagId.hashCode();
        return result;
    }
}
