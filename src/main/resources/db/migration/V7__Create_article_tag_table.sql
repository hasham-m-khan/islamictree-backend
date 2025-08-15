CREATE TABLE IF NOT EXISTS article_tag (
    article_id BIGINT,
    tag_id BIGINT,

    PRIMARY KEY (article_id, tag_id),

    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;