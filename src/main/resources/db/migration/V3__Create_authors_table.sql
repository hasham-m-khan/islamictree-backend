CREATE TABLE IF NOT EXISTS authors (
    id              BIGINT          AUTO_INCREMENT,
    first_name      VARCHAR(255)    NOT NULL,
    last_name       VARCHAR(255)    NOT NULL,
    birth_date      DATE,
    death_date      DATE,
    birth_place     VARCHAR(255),
    resting_place   VARCHAR(255),
    description     TEXT            NOT NULL,
    image_data      BLOB,
    mime_type       VARCHAR(100),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT chk_names_not_empty
        CHECK (TRIM(first_name) != '' AND TRIM(last_name) != ''),
    CONSTRAINT chk_death_after_birth
        CHECK (death_date IS NULL OR birth_date IS NULL OR death_date >= birth_date),
    CONSTRAINT chk_mime_type_format
        CHECK (mime_type IS NULL OR mime_type REGEXP '^[a-z]+/[a-z0-9][a-z0-9!#$&\\-\\^_]*$'),
    CONSTRAINT chk_img_content_consistency
        CHECK ((image_data IS NULL AND mime_type IS NULL) OR
            (image_data IS NOT NULL AND mime_type IS NOT NULL))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_authors_first_name   ON authors(first_name);
CREATE INDEX idx_authors_last_name    ON authors(last_name);
CREATE INDEX idx_authors_full_name    ON authors(last_name, first_name);
CREATE INDEX idx_authors_birth_date   ON authors(birth_date);
CREATE INDEX idx_authors_birth_place  ON authors(birth_place);
CREATE INDEX idx_authors_created_at   ON authors(created_at);

${authorsFullTextIndexes}
