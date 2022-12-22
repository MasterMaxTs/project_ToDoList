package ru.job4j.todo.service.item.priority;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

/**
 * Сервис приоритетов задач
 */
public interface PriorityService {

    /**
     * Возвращает список приоритетов
     * @return список всех приоритетов
     */
    List<Priority> findAll();

    /**
     * Находит приоритет задачи по его числовому значению
     * @param position числовое значение приоритета
     * @return Optional.of(priority), если приоритет найден,
     * иначе Optional.empty()
     */
    Optional<Priority> findByPosition(int position);
}
