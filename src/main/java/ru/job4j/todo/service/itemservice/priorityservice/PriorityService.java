package ru.job4j.todo.service.itemservice.priorityservice;

import ru.job4j.todo.entity.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityService {

    List<Priority> findAll();

    Optional<Priority> findByPosition(int position);
}
