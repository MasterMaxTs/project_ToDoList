package ru.job4j.todo.repository.item.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация хранилища категорий
 */
@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    /**
     * Делегирование выполнения CRUD-операций
     * @see ru.job4j.todo.repository.crud.CrudRepositoryImpl
     */
    private final CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                "from Category where id = :fId",
                Category.class,
                Map.of("fId", id)
        );
    }
}
