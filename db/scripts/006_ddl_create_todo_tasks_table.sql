CREATE TABLE IF NOT EXISTS todo_tasks
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    description TEXT NOT NULL,
    created     TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    done        BOOLEAN,
    user_id     INT NOT NULL REFERENCES todo_users (id),
    priority_id INT REFERENCES todo_priorities (id)
);


comment on table todo_tasks is 'Задачи';
comment on column todo_tasks.id is 'Идентификатор задачи - первичный ключ';
comment on column todo_tasks.name is 'Название задачи';
comment on column todo_tasks.description is 'Описание задачи';
comment on column todo_tasks.created is 'Локальное время создания задачи';
comment on column todo_tasks.done is 'Статус завершённости задачи';
comment on column todo_tasks.user_id is 'Идентификатор пользователя - внешний ключ';
comment on column todo_tasks.priority_id is 'Идентификатор приоритета задачи - внешний ключ';
