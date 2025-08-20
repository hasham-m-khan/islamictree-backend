package com.islamictree.start.repositories;

import com.islamictree.start.models.Article;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends R2dbcRepository<Article, Long> {
}
