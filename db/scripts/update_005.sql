CREATE TABLE IF NOT EXISTS todo_categories
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL
);

INSERT INTO todo_categories (name) VALUES ('Семья');
INSERT INTO todo_categories (name) VALUES ('Учёба');
INSERT INTO todo_categories (name) VALUES ('Домашние дела');
INSERT INTO todo_categories (name) VALUES ('Автомобиль');
INSERT INTO todo_categories (name) VALUES ('Покупки');
