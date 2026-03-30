CREATE TABLE users
(
    id         uuid primary key,
    username   varchar(100) not null unique,
    password   varchar(255) not null,
    firstname  varchar(100) not null,
    lastname   varchar(100) not null,
    created_at TIMESTAMP WITH TIME ZONE    not null default now(),
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE expenses
(
    id          uuid primary key,
    title       varchar(55) not null,
    amount double not null,
    user_id     uuid        not null references users (id),
    labels      text ARRAY,
    expended_at TIMESTAMP WITH TIME ZONE   not null,
    created_at  TIMESTAMP WITH TIME ZONE   not null default now(),
    updated_at  TIMESTAMP WITH TIME ZONE
);

INSERT INTO users (id, username, password, firstname, lastname, created_at, updated_at)
VALUES ('a2cc64db-b745-4ee1-83e7-27fae887d1c6', 'gui', '123456', 'gui', 'aleixo', now(), null);

INSERT INTO expenses (id, title, amount, user_id, labels, expended_at, updated_at)
VALUES ('a2cc64db-b745-4ee1-83e7-27fae887d1c6', 'milk', -12.59, 'a2cc64db-b745-4ee1-83e7-27fae887d1c6', ('GROCERY'),
        '2026-03-22T01:37:26Z', null);
INSERT INTO expenses (id, title, amount, user_id, labels, expended_at, updated_at)
VALUES ('a2cc64db-b745-4ee1-83e7-27fae887d1c7', 'fuel', -250.0, 'a2cc64db-b745-4ee1-83e7-27fae887d1c6', ('CAR'),
        '2026-03-23T01:37:26Z', null);
INSERT INTO expenses (id, title, amount, user_id, labels, expended_at, updated_at)
VALUES ('a2cc64db-b745-4ee1-83e7-27fae887d1c8', 'thermal paste', -5.00, 'a2cc64db-b745-4ee1-83e7-27fae887d1c6', ('COMPUTER'),
        '2026-03-24T01:37:26Z', null);