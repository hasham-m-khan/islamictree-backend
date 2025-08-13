package com.islamictree.start.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table(name = "author_article")
public class AuthorArticle {

    @Column("author_id")
    private Long authorId;

    @Column("article_id")
    private Long articleId;
}
