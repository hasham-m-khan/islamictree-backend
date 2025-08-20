package com.islamictree.start.repositories;

import com.islamictree.start.models.Author;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Long> {
}
