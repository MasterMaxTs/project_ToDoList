package ru.job4j.todo.persistence.userstore;

import ru.job4j.todo.entity.User;

import java.util.Optional;

public interface UserStore extends AutoCloseable {

    User add(User user);

    User update(User user);

    Optional<User> findUserByLoginAndPwd(String login, String password);

    boolean findUserByLogin(String login);
}
