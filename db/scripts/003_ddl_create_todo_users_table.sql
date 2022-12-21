CREATE TABLE IF NOT EXISTS todo_users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR NOT NULL,
    login    VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    time_zone_id INT REFERENCES todo_time_zones(id)
);


comment on table todo_users is 'Пользователи';
comment on column todo_users.id is 'Идентификатор пользователя - первичный ключ';
comment on column todo_users.name is 'Имя пользователя';
comment on column todo_users.login is 'Логин пользователя';
comment on column todo_users.password is 'Пароль пользователя';
comment on column todo_users.time_zone_id is 'Идентификатор часового пояса - внешний ключ';
