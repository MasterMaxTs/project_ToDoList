package ru.job4j.todo.repository.timezone;

import ru.job4j.todo.model.TimeZone;

import java.util.List;

/**
 * Хранилище для часовых поясов
 */
public interface TimeZoneRepository {

    /**
     * Возвращает список всех часовых поясов, находящихся в базе данных
     * @return список всех часовых поясов
     */
    List<TimeZone> findAll();

    /**
     * Находит часовой пояс в базе данных по ID
     * @param tzId идентификатор часового пояса
     * @return часовой пояс, если он найден,
     * иначе выбрасывает исключение
     */
    TimeZone findById(int tzId);
}
