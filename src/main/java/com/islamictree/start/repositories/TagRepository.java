package com.islamictree.start.repositories;

import com.islamictree.start.models.Tag;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends R2dbcRepository<Tag, Long> {
}
