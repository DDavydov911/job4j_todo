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
    created TIMESTAMP default CURRENT_TIMESTAMP,
    done BOOLEAN,
    user_id INTEGER NOT NULL REFERENCES users (id)
);

INSERT INTO items(description, done, user_id) VALUES('Task 1', false, 1);
INSERT INTO items(description, done, user_id) VALUES('Task 2', false, 1);

CREATE TABLE IF NOT EXISTS categoties (
    id SERIAL PRIMARY KEY,
    `name` VARCHAR(255)
);

INSERT INTO items(name) VALUES('Home');
INSERT INTO items(name) VALUES('Family');
INSERT INTO items(name) VALUES('Health');
INSERT INTO items(name) VALUES('Job');

CREATE TABLE items_categories(
    id serial primary key,
    item_id integer references item(id),
    categories_id integer references categories(id)
);

INSERT INTO items_categories(item_id, categories_id) VALUES(1, 1);
INSERT INTO items_categories(item_id, categories_id) VALUES(1, 2);
INSERT INTO items_categories(item_id, categories_id) VALUES(2, 3);
INSERT INTO items_categories(item_id, categories_id) VALUES(2, 4);