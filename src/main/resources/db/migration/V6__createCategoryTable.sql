CREATE TABLE IF NOT EXISTS category
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    description VARCHAR(30)
);

