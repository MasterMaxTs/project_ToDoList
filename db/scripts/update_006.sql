CREATE TABLE IF NOT EXISTS todo_tasks_categories
(
    PRIMARY KEY (task_id, category_id),
    task_id INT NOT NULL REFERENCES todo_tasks(id),
    category_id INT NOT NULL REFERENCES todo_categories(id)
);