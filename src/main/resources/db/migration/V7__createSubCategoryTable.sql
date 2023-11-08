CREATE TABLE IF NOT EXISTS sub_category
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL UNIQUE,
    description VARCHAR(30),
    category_id BIGINT,
    FOREIGN KEY (category_id) references category (id)
);

