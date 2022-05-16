CREATE TABLE IF NOT EXISTS users (
    id          SERIAL PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL
);

INSERT INTO users(username, email, password) VALUES('John', 'abc@mail.com', '123');
INSERT INTO users(username, email, password) VALUES('Ivan', 'yes@mail.com', 'qwerty');

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    created TIMESTAMP,
    done BOOLEAN,
    user_id INTEGER NOT NULL REFERENCES users (id)
);
-- Как добавить поле timestamp в H2? Пишет: Column count does not match; SQL statement:
--
--
-- INSERT INTO items(description, created, done) VALUES('Task 1', current_timestamp, false, 1);
-- INSERT INTO items(description, created, done) VALUES('Task 2', current_timestamp, false, 1); [21002-200]


-- INSERT INTO items(description, created, done) VALUES('Task 1', current_timestamp, false, 1);
-- INSERT INTO items(description, created, done) VALUES('Task 2', current_timestamp, false, 1);