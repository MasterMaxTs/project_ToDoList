CREATE TABLE IF NOT EXISTS todo_priorities
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    position INT NOT NULL
);


comment on table todo_priorities is 'Приоритеты задач';
comment on column todo_priorities.id is 'Идентификатор приоритета';
comment on column todo_priorities.name is 'Название приоритета';
comment on column todo_priorities.position is 'Состояние приоритета';