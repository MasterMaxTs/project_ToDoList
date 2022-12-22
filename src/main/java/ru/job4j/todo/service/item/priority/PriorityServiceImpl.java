package ru.job4j.todo.service.item.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.item.priority.PriorityRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriorityServiceImpl implements PriorityService {

    private PriorityRepository store;

    @Override
    public List<Priority> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<Priority> findByPosition(int position) {
        return store.findByPosition(position);
    }
}