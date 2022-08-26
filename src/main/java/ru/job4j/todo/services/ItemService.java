package ru.job4j.todo.services;

import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.persistence.ItemStore;

import java.util.List;

@Service
public class ItemService {

    private final ItemStore store;

    public ItemService(ItemStore store) {
        this.store = store;
    }

    public Item create(Item item) {
        return store.create(item);
    }

    public List<Item> findAll() {
        return store.findAll();
    }

    public List<Item> findCompleted() {
        return store.findCompleted();
    }

    public List<Item> findNew() {
        return store.findNew();
    }

    public boolean update(Item item) {
        return store.update(item);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public Item findById(int id) {
        return store.findById(id);
    }
}
