package ru.job4j.todo.service.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.item.ItemRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Реализация сервиса задач
 */
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    /**
     * Делегирование выполнения CRUD-операций хранилищу задач
     */
    private final ItemRepository store;

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
