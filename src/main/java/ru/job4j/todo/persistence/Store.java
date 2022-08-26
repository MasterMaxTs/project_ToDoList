package ru.job4j.todo.persistence;

import java.util.List;

public interface Store<T> extends AutoCloseable {

    T create(T item);

    List<T> findAll();

    boolean update(T item);

    boolean delete(int id);

    T findById(int id);
}
