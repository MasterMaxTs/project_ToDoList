package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.category.CategoryRepository;

import java.util.List;

/**
 * Реализация сервиса категорий задач
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    /**
     * Делегирование выполнения CRUD-операций хранилищу категорий задач
     */
    private final CategoryRepository store;

    @Override
    public List<Category> findAll() {
        return store.findAll();
    }

    @Override
    public Category findById(int id) {
        return store.findById(id);
    }
}
