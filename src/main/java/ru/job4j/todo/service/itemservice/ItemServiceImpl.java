package ru.job4j.todo.service.itemservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.persistence.itemstore.ItemStore;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemStore store;

    @Override
    public Item create(Item item) {
        return store.create(item);
    }

    @Override
    public List<Item> findAll(int userId) {
        return store.findAll(userId);
    }

    @Override
    public List<Item> findCompleted(int userId) {
        return store.findCompleted(userId);
    }

    @Override
    public List<Item> findNew(int userId) {
        return store.findNew(userId);
    }

    @Override
    public boolean update(Item item) {
        return store.update(item);
    }

    @Override
    public boolean delete(int id, int userId) {
        return store.delete(id, userId);
    }

    public Item findById(int id, int userId) {
        return store.findById(id, userId);
    }
}
