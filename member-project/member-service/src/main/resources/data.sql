CREATE TABLE IF NOT EXISTS member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    version INT NOT NULL
    ) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

show tables;

CREATE DATABASE IF NOT EXISTS `history`;