CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN
);

INSERT INTO items(
    description, done)
VALUES('Описание 1', false),
      ('Описание 2', false);