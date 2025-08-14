-------------------
-- ADDRESS TABLE --
-------------------
CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT AUTO_INCREMENT,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(100) NOT NULL DEFAULT 'USA',
    zip_code VARCHAR(20) NOT NULL,
    latitude DECIMAL(10,8) SIGNED,
    longitude DECIMAL (10, 8) SIGNED,
    is_validated BOOLEAN DEFAULT FALSE,
    address_type ENUM('RESIDENTIAL', 'BUSINESS', 'PO_BOX', 'OTHER') DEFAULT 'RESIDENTIAL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE INDEX idx_addresses_city ON addresses (city);
CREATE INDEX idx_addresses_state ON addresses (state);
CREATE INDEX idx_addresses_zip_code ON addresses (zip_code);
CREATE INDEX idx_addresses_validated ON addresses(is_validated);
CREATE INDEX idx_addresses_type ON addresses(address_type);
CREATE INDEX idx_addresses_city_state ON addresses (city, state);

ALTER TABLE addresses
    ADD CONSTRAINT chk_zip_code_format
    CHECK (zip_code REGEXP '^[0-9]{5}(-[0-9]{4})?$');

ALTER TABLE addresses
    ADD CONSTRAINT chk_state_code_length
    CHECK (LENGTH(state) <= 3);

ALTER TABLE addresses
    ADD CONSTRAINT chk_country_not_empty
    CHECK (LENGTH(TRIM(country)) > 0);


-------------------
--  USERS TABLE  --
-------------------
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    telephone VARCHAR(12),
    address_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES addresses(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
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
    PRIMARY KEY (article_id, tag_id),
    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);

-- AUTHOR_ARTICLE TABLE
CREATE TABLE IF NOT EXISTS author_article (
    author_id BIGINT,
    article_id BIGINT,
    PRIMARY KEY (author_id, article_id),
    FOREIGN KEY (author_id) REFERENCES authors(id),
    FOREIGN KEY (article_id) REFERENCES articles(id)
);
