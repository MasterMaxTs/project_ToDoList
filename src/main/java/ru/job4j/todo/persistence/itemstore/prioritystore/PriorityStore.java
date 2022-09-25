package ru.job4j.todo.persistence.itemstore.prioritystore;

import ru.job4j.todo.entity.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityStore {
    List<Priority> findAll();
    Optional<Priority> findByPosition(int position);
}
