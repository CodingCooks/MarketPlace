CREATE TABLE IF NOT EXISTS users_saved_advertisements
(
    user_id          BIGINT,
    advertisement_id BIGINT,
    FOREIGN KEY (user_id) references users (id),
    FOREIGN KEY (advertisement_id) references advertisement (id),
    PRIMARY KEY (user_id, advertisement_id)
);