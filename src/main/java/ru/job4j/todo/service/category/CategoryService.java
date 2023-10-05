package ru.job4j.todo.service.category;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * Сервис категорий задач
 */
public interface CategoryService {

    /**
     * Возвращает список категорий
     * @return список всех категорий
     */
    List<Category> findAll();

    /**
     * Находит категорию по id
     * @param id id категории
     * @return категорию, если она найдена,
     * иначе выбрасывает исключение
     */
    Category findById(int id);
}
