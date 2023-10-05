package ru.job4j.todo.service.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.priority.PriorityRepository;

import java.util.List;

/**
 * Реализация сервиса приоритетов задач
 */
@Service
@AllArgsConstructor
public class PriorityServiceImpl implements PriorityService {

    /**
     * Делегирование выполнения CRUD-операций хранилищу приоритетов задач
     */
    private PriorityRepository store;

    @Override
    public List<Priority> findAll() {
        return store.findAll();
    }

    @Override
    public Priority findByPosition(int position) {
        return store.findByPosition(position);
    }
}
