package ru.job4j.todo.services;

import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.persistence.ItemStoreImpl;

import java.util.List;

@Service
public class ItemService {

    private final ItemStoreImpl store;

    public ItemService(ItemStoreImpl store) {
        this.store = store;
    }

    public Item create(Item item) {
        return store.create(item);
    }

    public List<Item> findAll(int userId) {
        return store.findAll(userId);
    }

    public List<Item> findCompleted(int userId) {
        return store.findCompleted(userId);
    }

    public List<Item> findNew(int userId) {
        return store.findNew(userId);
    }

    public boolean update(Item item) {
        return store.update(item);
    }

    public boolean delete(int id, int userId) {
        return store.delete(id, userId);
    }

    public Item findById(int id, int userId) {
        return store.findById(id, userId);
    }
}
