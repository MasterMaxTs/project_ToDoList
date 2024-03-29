package ru.job4j.todo.repository.timezone;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.TimeZone;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
    public TimeZone findById(int tzId) {
        return crudRepository.optional(
                "from TimeZone where id = :fId",
                TimeZone.class,
                Map.of("fId", tzId)
        ).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Часовой пояс с id = %d"
                                + " не найден в БД! ", tzId)
                ));
    }
}
