CREATE TABLE IF NOT EXISTS todo_time_zones
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    utc_offset CHAR(3) NOT NULL
);


comment on table todo_time_zones is 'Часовые пояса';
comment on column todo_time_zones.id is 'Идентификатор часового пояса';
comment on column todo_time_zones.name is 'Название часового пояса';
comment on column todo_time_zones.utc_offset is 'Разница в часах и минутах между всемирным скоординированным временем (UTC) и местным временем';
