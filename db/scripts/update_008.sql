CREATE TABLE IF NOT EXISTS todo_time_zones
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    utc_offset CHAR(3) NOT NULL
);

INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Europe/Kaliningrad', '+2');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Europe/Moscow', '+3');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Europe/Samara', '+4');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Yekaterinburg', '+5');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Omsk', '+6');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Novosibirsk', '+7');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Irkutsk', '+8');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Chita', '+9');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Vladivostok', '+10');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Sakhalin', '+11');
INSERT INTO todo_time_zones (name,utc_offset) VALUES ('Asia/Kamchatka', '+12');
