package ru.job4j.todo.service.itemservice.categoryservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.Category;
import ru.job4j.todo.persistence.itemstore.categorystore.CategoryStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryStore store;

    @Override
    public List<Category> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return store.findById(id);
    }
}
