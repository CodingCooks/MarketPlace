CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(50)  NOT NULL UNIQUE,
    first_name       VARCHAR(50),
    last_name       VARCHAR(50),
    username       VARCHAR(50),
    password    VARCHAR(256) NOT NULL,
    role_id     BIGINT,
    is_verified BOOLEAN,
    is_active BOOLEAN,
    FOREIGN KEY (role_id) references role (id)
);

