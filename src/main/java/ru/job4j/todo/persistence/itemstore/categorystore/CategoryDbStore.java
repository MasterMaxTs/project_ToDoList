package ru.job4j.todo.persistence.itemstore.categorystore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.Category;
import ru.job4j.todo.persistence.crudrepository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryDbStore implements CategoryStore {

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
