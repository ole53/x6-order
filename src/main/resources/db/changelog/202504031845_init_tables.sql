CREATE SCHEMA x6_application;

CREATE TABLE x6_application.order
(
    id              SERIAL         PRIMARY KEY,
    user_id         NUMERIC        NOT NULL REFERENCES x6_application.user (id),
    total_sum       NUMERIC,
    date_create     DATE           NOT NULL,
    delivery_moment DATE,
    draft           NUMERIC(1)     NOT NULL DEFAULT 1,
    is_active       NUMERIC(1)     NOT NULL DEFAULT 1
);

CREATE TABLE x6_application.basket
(
    id              SERIAL      PRIMARY KEY,
    order_id        NUMERIC     NOT NULL REFERENCES x6_application.order (id)
);

CREATE TABLE x6_application.product_in_basket
(
    id              SERIAL      PRIMARY KEY,
    basket_id       NUMERIC     NOT NULL REFERENCES x6_application.basket (id),
    product_id      NUMERIC     NOT NULL REFERENCES x6_application.product (id),
    product_count   NUMERIC     NOT NULL,
    position        NUMERIC     NOT NULL,
    in_stock        NUMERIC(1)  NOT NULL
);