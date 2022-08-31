package ru.job4j.todo.persistence;

import ru.job4j.todo.entity.User;

import java.util.List;

public interface UserStore extends AutoCloseable {

    User add(User user);

    List<User> findAll();

    boolean update(User user);

    boolean delete(int id);

    User findById(int id);
}
