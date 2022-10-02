package ru.job4j.todo.persistence.timezonestore;

import ru.job4j.todo.entity.TimeZone;

import java.util.List;
import java.util.Optional;

public interface TimeZoneStore {

    List<TimeZone> findAll();

    Optional<TimeZone> findById(int tzId);
}
