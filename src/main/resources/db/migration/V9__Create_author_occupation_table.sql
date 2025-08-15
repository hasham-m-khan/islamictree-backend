CREATE TABLE IF NOT EXISTS author_occupation (
    author_id BIGINT,
    occupation_id BIGINT,

    PRIMARY KEY (author_id, occupation_id),

    FOREIGN KEY (author_id) REFERENCES authors(id),
    FOREIGN KEY (occupation_id) REFERENCES occupations(id)

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;