package ru.job4j.todo.service.itemservice.categoryservice;

import ru.job4j.todo.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Optional<Category> findById(int id);
}
