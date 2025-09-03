package com.islamictree.start.repositories;

import com.islamictree.start.models.Tag;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface TagRepository extends R2dbcRepository<Tag, Long> {

    @Query("""
        SELECT t.* FROM tags t
        INNER JOIN author_tag aut ON aut.tag_id = t.id
        WHERE aut.author_id = :authorId
    """)
    Flux<Tag> findTagsByAuthorId(Long authorId);

    @Query("""
        SELECT t.* FROM tag t
        INNER JOIN author_tag aut ON t.id = aut.tag_id
        WHERE aut.author_id IN (:authorIds)
    """)
    Flux<Tag> findTagsByAuthorIds(Collection<Long> authorIds);

    @Query("""
        SELECT t.* FROM tags t 
        INNER JOIN article_tag art ON t.id = art.tag_id
        WHERE art.article_id = :articleId
    """)
    Flux<Tag> findTagsByArticleId(Long articleId);
}
