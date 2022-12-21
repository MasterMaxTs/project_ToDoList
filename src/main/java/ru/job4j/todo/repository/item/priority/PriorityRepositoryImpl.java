package ru.job4j.todo.repository.item.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriorityRepositoryImpl implements PriorityRepository {

    private CrudRepository crudRepository;

    @Override
    public List<Priority> findAll() {
        return crudRepository.query("from Priority", Priority.class);
    }

    @Override
    public Optional<Priority> findByPosition(int position) {
        return crudRepository.optional(
                "from Priority where position = :fPos",
                Priority.class,
                Map.of("fPos", position)
        );
    }
}
