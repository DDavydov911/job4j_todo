CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN
);

INSERT INTO items(
    description, created, done)
VALUES('Task 1', current_timestamp, false),
      ('Task 2', current_timestamp, false);