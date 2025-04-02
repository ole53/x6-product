CREATE SCHEMA x6_application;

CREATE TABLE x6_application.product
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR        NOT NULL,
    price           NUMERIC        NOT NULL,
    producer        VARCHAR        NOT NULL,
    category        VARCHAR(30),
    description     VARCHAR,
    in_stock        NUMERIC(1)     NOT NULL,
    is_active       NUMERIC(1)
);