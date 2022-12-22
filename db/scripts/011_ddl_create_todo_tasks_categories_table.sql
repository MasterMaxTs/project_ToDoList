CREATE TABLE IF NOT EXISTS todo_tasks_categories
(
    PRIMARY KEY (task_id, category_id),
    task_id INT NOT NULL REFERENCES todo_tasks(id),
    category_id INT NOT NULL REFERENCES todo_categories(id)
);


comment on table todo_tasks_categories is 'Объединяющая таблица для задач и категорий';
comment on column todo_tasks_categories.task_id is 'Идентификатор задачи - внешний ключ';
comment on column todo_tasks_categories.category_id is 'Идентификатор категории - внешний ключ';