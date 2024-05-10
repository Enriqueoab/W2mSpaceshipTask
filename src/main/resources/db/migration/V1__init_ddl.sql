CREATE TABLE IF NOT EXISTS source (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    premiere_year INT NOT NULL,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS spaceship (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    source_id BIGINT,
    image_url VARCHAR(255),
    FOREIGN KEY (source_id) REFERENCES source(id)
);