CREATE TABLE IF NOT EXISTS album (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    artist varchar(128) NOT NULL,
    release_date DATE
);