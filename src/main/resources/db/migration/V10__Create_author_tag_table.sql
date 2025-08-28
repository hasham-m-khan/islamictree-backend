CREATE TABLE IF NOT EXISTS author_tag (
   author_id BIGINT,
   tag_id BIGINT,

   PRIMARY KEY (author_id, tag_id),

    FOREIGN KEY (author_id) REFERENCES authors(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;