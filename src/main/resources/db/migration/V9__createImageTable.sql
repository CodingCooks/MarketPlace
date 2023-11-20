CREATE TABLE IF NOT EXISTS image
(
    id               BIGSERIAL PRIMARY KEY,
    url  VARCHAR(100) NOT NULL UNIQUE,
    main bool,
    advertisement_id BIGINT,
    FOREIGN KEY (advertisement_id) references advertisement (id)
);

