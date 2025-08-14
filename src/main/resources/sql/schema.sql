-- ADDRESS TABLE
CREATE TABLE IF NOT EXISTS address (
    id BIGINT AUTO_INCREMENT,
    street_name VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip_code VARCHAR(255),
    longitude NUMERIC(8, 2) SIGNED,
    latitude NUMERIC(8, 2) SIGNED,
    PRIMARY KEY (id)
);

--  USERS TABLE
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    telephone VARCHAR(12),
    address_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

-- AUTHORS TABLE
CREATE TABLE IF NOT EXISTS authors (
    id BIGINT AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    description TEXT,
    image_data BLOB,
    content_type VARCHAR(255),
    PRIMARY KEY (id)
);

-- TAGS TABLE
CREATE TABLE IF NOT EXISTS tags (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

-- ARTICLE TABLE
CREATE TABLE IF NOT EXISTS articles (
    id BIGINT AUTO_INCREMENT,
    title VARCHAR(255),
    blurb TEXT,
    article TEXT,
    image_data BLOB,
    content_type VARCHAR(255),
    created_at TIMESTAMP,
    last_updated TIMESTAMP,
    PRIMARY KEY (id)
);


--------------------------------
-- MANY-TO-MANY RELATIONSHIPS --
--------------------------------

-- ARTICLE_TAG TABLE
CREATE TABLE IF NOT EXISTS article_tag (
    article_id BIGINT,
    tag_id BIGINT,
    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);

-- AUTHOR_ARTICLE TABLE
CREATE TABLE IF NOT EXISTS author_article (
    author_id BIGINT,
    article_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES authors(id),
    FOREIGN KEY (article_id) REFERENCES articles(id)
);
