package ru.job4j.todo.service.timezone;

import ru.job4j.todo.model.TimeZone;

import java.util.List;
import java.util.Optional;

public interface TimeZoneService {

    List<TimeZone> findAll();

    Optional<TimeZone> findById(int tzId);
}
