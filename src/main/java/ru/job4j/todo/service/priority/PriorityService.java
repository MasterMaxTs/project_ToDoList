package ru.job4j.todo.service.priority;

import ru.job4j.todo.model.Priority;

import java.util.List;

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
     * @return приоритет, если он найден,
     * иначе выбрасывает исключение
     */
    Priority findByPosition(int position);
}
