package ru.job4j.todo.service.timezone;

import ru.job4j.todo.model.TimeZone;

import java.util.List;

/**
 * Сервис часовых поясов
 */
public interface TimeZoneService {

    /**
     * Возвращает список всех часовых поясов
     * @return список всех часовых поясов
     */
    List<TimeZone> findAll();

    /**
     * Находит часовой пояс по ID
     * @param tzId идентификатор часового пояса
     * @return часовой пояс, если он найден,
     * иначе выбрасывает исключение
     */
    TimeZone findById(int tzId);
}
