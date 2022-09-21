package ru.job4j.todo.service.itemservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.entity.User;
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
    public List<Item> findAll() {
        return store.findAll();
    }

    @Override
    public List<Item> findAll(User user) {
        return store.findAll(user);
    }

    @Override
    public List<Item> findCompleted(User user) {
        return store.findCompleted(user);
    }

    @Override
    public List<Item> findNew(User user) {
        return store.findNew(user);
    }

    @Override
    public void update(Item item) {
        store.update(item);
    }

    @Override
    public void delete(int id, User user) {
        store.delete(id, user);
    }

    public Item findById(int id, User user) {
        return store.findById(id, user);
    }
}
