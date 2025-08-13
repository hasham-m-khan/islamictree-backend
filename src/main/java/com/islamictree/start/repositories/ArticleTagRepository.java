package com.islamictree.start.repositories;

import com.islamictree.start.model.Article;
import com.islamictree.start.model.ArticleTag;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ArticleTagRepository extends R2dbcRepository<ArticleTag, Long> {

    @Query("""
            SELECT a.*, t.* FROM articles AS a
            JOIN article_tag AS a_t ON a.id = a_t.article_id
            JOIN tags AS t ON t.id = a_t.tag_id
            WHERE t.id = :tagId
    """)
    Flux<Article> findByTagId(Long tagId);
}
