package ru.job4j.todo.repository.timezonestore;

import ru.job4j.todo.model.TimeZone;

import java.util.List;
import java.util.Optional;

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
     * @return Optional.of(timeZone), если часовой пояс найден,
     * иначе Optional.empty()
     */
    Optional<TimeZone> findById(int tzId);
}
