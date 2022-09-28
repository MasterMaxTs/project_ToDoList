CREATE TABLE IF NOT EXISTS todo_users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(40) NOT NULL,
    login    VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS todo_tasks
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100),
    description TEXT,
    created     TIMESTAMP,
    done        BOOLEAN,
    user_id     INT NOT NULL REFERENCES todo_users(id)
);