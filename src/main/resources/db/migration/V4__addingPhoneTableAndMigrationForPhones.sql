CREATE TABLE IF NOT EXISTS phone
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT,
    number     VARCHAR(100) NOT NULL,
    is_main    BOOLEAN,
    is_deleted BOOLEAN,
    UNIQUE (number),
    foreign key (user_id) references users (id)
);