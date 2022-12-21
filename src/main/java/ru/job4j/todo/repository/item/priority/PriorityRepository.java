package ru.job4j.todo.repository.item.priority;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityRepository {
    List<Priority> findAll();
    Optional<Priority> findByPosition(int position);
}
