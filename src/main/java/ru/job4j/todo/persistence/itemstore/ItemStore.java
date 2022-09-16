package ru.job4j.todo.persistence.itemstore;

import ru.job4j.todo.entity.Item;

import java.util.List;

public interface ItemStore {

    Item create(Item item);

    List<Item> findAll(int userId);

    List<Item> findCompleted(int userId);

    List<Item> findNew(int userId);

    void update(Item item);

    void delete(int id, int userId);

    Item findById(int id, int userId);
}
