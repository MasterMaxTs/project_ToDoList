package ru.job4j.todo.persistence.itemstore.categorystore;

import ru.job4j.todo.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryStore {

    List<Category> findAll();

    Optional<Category> findById(int id);
}
