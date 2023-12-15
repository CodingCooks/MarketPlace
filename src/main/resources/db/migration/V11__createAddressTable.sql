CREATE TABLE IF NOT EXISTS address
(
    id           BIGSERIAL PRIMARY KEY,
    city         VARCHAR(100) NOT NULL UNIQUE,
    full_address VARCHAR(100) NOT NULL UNIQUE,
    postal_code  INT          NOT NULL,
    country      VARCHAR(100) NOT NULL UNIQUE
);
alter table users
    add column address_id bigint;
ALTER TABLE users
    ADD CONSTRAINT fk_users_address
        FOREIGN KEY (address_id)
            REFERENCES address (id);

alter table advertisement
    add column address_id bigint;
ALTER TABLE advertisement
    ADD CONSTRAINT fk_users_advertisement
        FOREIGN KEY (address_id)
            REFERENCES address (id);

