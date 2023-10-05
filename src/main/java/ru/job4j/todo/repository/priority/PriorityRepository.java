package ru.job4j.todo.repository.priority;

import ru.job4j.todo.model.Priority;

import java.util.List;

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
     * @return приоритет, если он найден,
     * иначе выбрасывает исключение
     */
    Priority findByPosition(int position);
}
