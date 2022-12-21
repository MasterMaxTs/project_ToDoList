package ru.job4j.todo.repository.timezonestore;

import ru.job4j.todo.model.TimeZone;

import java.util.List;
import java.util.Optional;

public interface TimeZoneRepository {

    List<TimeZone> findAll();

    Optional<TimeZone> findById(int tzId);
}
