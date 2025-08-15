CREATE TABLE IF NOT EXISTS author_article (
    author_id BIGINT,
    article_id BIGINT,

    PRIMARY KEY (author_id, article_id),

    FOREIGN KEY (author_id) REFERENCES authors(id),
    FOREIGN KEY (article_id) REFERENCES articles(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;