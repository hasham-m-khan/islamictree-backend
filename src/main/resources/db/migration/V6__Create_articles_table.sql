CREATE TABLE IF NOT EXISTS articles (
    id BIGINT AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    blurb TEXT,
    content TEXT NOT NULL,
    source VARCHAR(255),
    date_published DATE,
    image_data BLOB,
    mime_type VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    INDEX idx_articles_title (title),
    INDEX idx_articles_date_published (date_published),
    FULLTEXT INDEX ft_articles_content (content)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;