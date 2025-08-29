CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT AUTO_INCREMENT,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(100) NOT NULL DEFAULT 'USA',
    zip_code VARCHAR(20) NOT NULL,
    latitude DECIMAL(10,8),
    longitude DECIMAL (10, 8),
    is_validated BOOLEAN DEFAULT FALSE,
    address_type VARCHAR(20) DEFAULT 'RESIDENTIAL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT chk_address_type CHECK (address_type IN ('RESIDENTIAL', 'BUSINESS', 'PO_BOX', 'OTHER')),
    CONSTRAINT chk_zip_code_format CHECK (zip_code REGEXP '^[0-9]{5}(-[0-9]{4})?$'),
    CONSTRAINT chk_state_code_length CHECK (LENGTH(state) <= 3),
    CONSTRAINT chk_country_not_empty CHECK (LENGTH(TRIM(country)) > 0),

    INDEX idx_addresses_city (city),
    INDEX idx_addresses_state (state),
    INDEX idx_addresses_zip_code (zip_code),
    INDEX idx_addresses_validated (is_validated),
    INDEX idx_addresses_type (address_type),
    INDEX idx_addresses_city_state (city, state)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;