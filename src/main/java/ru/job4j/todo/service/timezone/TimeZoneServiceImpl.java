package ru.job4j.todo.service.timezone;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.TimeZone;
import ru.job4j.todo.repository.timezonestore.TimeZoneRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса часовых поясов
 */
@Service
@AllArgsConstructor
public class TimeZoneServiceImpl implements TimeZoneService {

    /**
     * Делегирование выполнения CRUD-операций хранилищу часовых поясов
     */
    private final TimeZoneRepository store;

    @Override
    public List<TimeZone> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<TimeZone> findById(int tzId) {
        return store.findById(tzId);
    }
}

