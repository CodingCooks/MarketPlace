CREATE TABLE IF NOT EXISTS image
(
    id               BIGSERIAL PRIMARY KEY,
    url              VARCHAR(30) NOT NULL UNIQUE,
    is_main          bool,
    advertisement_id BIGINT,
    FOREIGN KEY (advertisement_id) references advertisement (id)
);

