package ru.job4j.todo.repository.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Реализация хранилища приоритетов задач
 */
@Repository
@AllArgsConstructor
public class PriorityRepositoryImpl implements PriorityRepository {

    /**
     * Делегирование выполнения CRUD-операций
     * @see ru.job4j.todo.repository.crud.CrudRepositoryImpl
     */
    private CrudRepository crudRepository;

    @Override
    public List<Priority> findAll() {
        return crudRepository.query("from Priority", Priority.class);
    }

    @Override
    public Priority findByPosition(int position) {
        return crudRepository.optional(
                "from Priority where position = :fPos",
                Priority.class,
                Map.of("fPos", position)
        ).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Приоритет с position = %d"
                                + " не найден в БД! ", position)
                )
        );
    }
}
