package com.islamictree.start.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "article_tag")
public class ArticleTag extends BaseEntity {

    @Column("article_id")
    private Long articleId;

    @Column("tag_id")
    private Long tagId;
}
