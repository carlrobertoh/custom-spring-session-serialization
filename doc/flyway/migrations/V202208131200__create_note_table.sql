CREATE TABLE note
(
    id      SERIAL PRIMARY KEY,
    author  VARCHAR(64),
    message VARCHAR(255) NOT NULL
);
