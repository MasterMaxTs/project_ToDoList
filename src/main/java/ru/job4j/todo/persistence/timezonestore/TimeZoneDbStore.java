package ru.job4j.todo.persistence.timezonestore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.TimeZone;
import ru.job4j.todo.persistence.crudrepository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TimeZoneDbStore implements TimeZoneStore {

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
