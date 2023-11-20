CREATE TABLE IF NOT EXISTS advertisement
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(30)  NOT NULL UNIQUE,
    description     VARCHAR(30)  NOT NULL,
    location        VARCHAR(200) NOT NULL,
    is_active       bool,
    creation_date   date,
    category_id     BIGINT       NOT NULL,
    sub_category_id BIGINT,
    FOREIGN KEY (category_id) references category (id),
    FOREIGN KEY (sub_category_id) references sub_category (id)
);
