package ru.job4j.todo.service.timezoneservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.TimeZone;
import ru.job4j.todo.persistence.timezonestore.TimeZoneStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TimeZoneServiceImpl implements TimeZoneService {

    private final TimeZoneStore store;

    @Override
    public List<TimeZone> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<TimeZone> findById(int tzId) {
        return store.findById(tzId);
    }
}

