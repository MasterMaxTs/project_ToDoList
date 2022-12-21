package ru.job4j.todo.service.item.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.item.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository store;

    @Override
    public List<Category> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return store.findById(id);
    }
}
