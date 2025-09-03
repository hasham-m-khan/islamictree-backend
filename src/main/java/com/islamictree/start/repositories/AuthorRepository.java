package com.islamictree.start.repositories;

import com.islamictree.start.models.Author;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Long> {

    @Query("""
        SELECT au.* FROM authors au
        INNER JOIN author_article aa ON au.id = aa.author_id
        WHERE aa.article_id = :articleId
    """)
    Flux<Author> findAuthorsByArticleId(Long articleId);
}
