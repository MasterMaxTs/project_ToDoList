package ru.job4j.todo.repository.item.priority;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище для приоритетов задач
 */
public interface PriorityRepository {

    /**
     * Возвращает список всех приоритетов, находящихся в базе данных
     * @return список всех приоритетов
     */
    List<Priority> findAll();

    /**
     * Находит приоритет в базе данных по его числовому значению
     * @param position числовое значение приоритета
     * @return Optional.of(priority), если приоритет найден,
     * иначе Optional.empty()
     */
    Optional<Priority> findByPosition(int position);
}
