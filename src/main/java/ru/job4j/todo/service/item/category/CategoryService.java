package ru.job4j.todo.service.item.category;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

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
     * @return Optional.of(category), если категория найдена,
     * иначе Optional.empty()
     */
    Optional<Category> findById(int id);
}
