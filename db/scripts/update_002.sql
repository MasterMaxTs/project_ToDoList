CREATE TABLE IF NOT EXISTS todo_priorities
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    position INT NOT NULL
);

INSERT INTO todo_priorities (name, position) VALUES ('срочный', 1);
INSERT INTO todo_priorities (name, position) VALUES ('обычный', 2);
INSERT INTO todo_priorities (name, position) VALUES ('не срочный', 3);