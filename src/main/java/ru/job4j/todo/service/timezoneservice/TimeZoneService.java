package ru.job4j.todo.service.timezoneservice;

import ru.job4j.todo.entity.TimeZone;

import java.util.List;
import java.util.Optional;

public interface TimeZoneService {

    List<TimeZone> findAll();

    Optional<TimeZone> findById(int tzId);
}
