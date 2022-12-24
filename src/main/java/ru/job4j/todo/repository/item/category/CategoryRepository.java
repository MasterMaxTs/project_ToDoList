package ru.job4j.todo.repository.item.category;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * Хранилище для категорий задач
 */
public interface CategoryRepository {

    /**
     * Возвращает список всех категорий, находящихся в базе данных
     * @return список всех категорий
     */
    List<Category> findAll();

    /**
     * Находит категорию в базе данных по id
     * @param id id категории
     * @return катагорию, если она найдена,
     * иначе выбрасывает исключение
     */
    Category findById(int id);
}
