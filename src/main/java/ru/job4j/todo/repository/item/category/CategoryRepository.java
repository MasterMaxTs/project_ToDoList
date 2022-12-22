package ru.job4j.todo.repository.item.category;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

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
     * @return Optional.of(category), если категория найдена,
     * иначе Optional.empty()
     */
    Optional<Category> findById(int id);
}
