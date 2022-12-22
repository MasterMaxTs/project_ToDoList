package ru.job4j.todo.repository.timezonestore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.TimeZone;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация хранилища часовых поясов
 */
@Repository
@AllArgsConstructor
public class TimeZoneRepositoryImpl implements TimeZoneRepository {

    /**
     * Делегирование выполнения CRUD-операций
     * @see ru.job4j.todo.repository.crud.CrudRepositoryImpl
     */
    private final CrudRepository crudRepository;

    @Override
    public List<TimeZone> findAll() {
        return crudRepository.query("from TimeZone", TimeZone.class);
    }

    @Override
    public Optional<TimeZone> findById(int tzId) {
        return crudRepository.optional(
                "from TimeZone where id = :fId",
                TimeZone.class,
                Map.of("fId", tzId)
        );
    }
}
