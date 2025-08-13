package com.islamictree.start.repositories;

import com.islamictree.start.model.Article;
import com.islamictree.start.model.AuthorArticle;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AuthorArticleRepository extends R2dbcRepository<AuthorArticle, Long> {
    @Query("""
        SELECT au.*, ar.* FROM authors AS au 
        JOIN author_article AS au_ar ON au.id = au_ar.auhtor_id
        JOIN article AS ar ON ar.id = au_ar.article_id
        WHERE au.id = :authorId
    """)
    Flux<Article> findByTagId(Long authorId);
}
