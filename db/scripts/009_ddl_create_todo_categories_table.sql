CREATE TABLE IF NOT EXISTS todo_categories
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);


comment on table todo_categories is 'Категории задач';
comment on column todo_categories.id is 'Идентификатор категории';
comment on column todo_categories.name is 'Наименование категории';
