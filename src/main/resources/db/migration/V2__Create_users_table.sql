CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    telephone VARCHAR(12),
    address_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    email_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    FOREIGN KEY (address_id) REFERENCES addresses(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    ${usersEmailConstraint}
    CONSTRAINT chk_password_length CHECK (CHAR_LENGTH(password_hash) >= 8),

    INDEX idx_users_email (email),
    INDEX idx_users_active (is_active),
    INDEX idx_users_created_at (created_at)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;