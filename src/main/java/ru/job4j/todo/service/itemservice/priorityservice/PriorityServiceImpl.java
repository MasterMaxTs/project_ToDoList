package ru.job4j.todo.service.itemservice.priorityservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.Priority;
import ru.job4j.todo.persistence.itemstore.prioritystore.PriorityStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriorityServiceImpl implements PriorityService {

    private PriorityStore store;

    @Override
    public List<Priority> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<Priority> findByPosition(int position) {
        return store.findByPosition(position);
    }
}
