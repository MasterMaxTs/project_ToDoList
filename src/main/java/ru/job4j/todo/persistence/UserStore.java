package ru.job4j.todo.persistence;

import ru.job4j.todo.entity.User;

import java.util.Optional;

public interface UserStore extends AutoCloseable {

    User add(User user);

    boolean update(User user);

    Optional<User> findUserByLoginAndPwd(String login, String password);

    boolean findUserByLogin(String login);
}
