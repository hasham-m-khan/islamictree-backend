package com.islamictree.start.repositories;

import com.islamictree.start.models.AuthorTag;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface AuthorTagRepository extends R2dbcRepository<AuthorTag, Long> {
    @Query("SELECT * FROM author_tag WHERE author_id = :authorId")
    Flux<AuthorTag> findByAuthorId(Long authorId);

    @Query("SELECT * FROM author_tag WHERE author_id IN (:authorIds)")
    Flux<AuthorTag> findByAuthorIds(Collection<Long> authorIds);

    @Query("DELETE FROM author_tag WHERE author_id = :authorId")
    Mono<Void> deleteByAuthorId(Long authorId);
}
