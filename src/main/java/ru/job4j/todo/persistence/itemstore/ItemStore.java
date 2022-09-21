package ru.job4j.todo.persistence.itemstore;

import ru.job4j.todo.entity.Item;
import ru.job4j.todo.entity.User;

import java.util.List;

public interface ItemStore {

    Item create(Item item);

    List<Item> findAll();

    List<Item> findAll(User user);

    List<Item> findCompleted(User user);

    List<Item> findNew(User user);

    void update(Item item);

    void delete(int id, User user);

    Item findById(int id, User user);
}
