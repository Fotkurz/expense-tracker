CREATE TABLE users
(
    id         uuid primary key,
    username   varchar(100) not null unique,
    password   varchar(255) not null,
    firstname  varchar(100) not null,
    lastname   varchar(100) not null,
    created_at timestamp    not null default now(),
    updated_at timestamp
);

CREATE TABLE expenses
(
    id          uuid primary key,
    title       varchar(55) not null,
    amount double not null,
    user_id     uuid        not null references users (id),
    labels      text ARRAY,
    expended_at timestamp   not null,
    created_at  timestamp   not null default now(),
    updated_at  timestamp
);

INSERT INTO users (id, username, password, firstname, lastname, created_at, updated_at)
VALUES ('a2cc64db-b745-4ee1-83e7-27fae887d1c6', 'gui', '123456', 'gui', 'aleixo', now(), null);
