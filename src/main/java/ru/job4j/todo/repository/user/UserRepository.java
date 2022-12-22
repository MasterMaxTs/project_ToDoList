package ru.job4j.todo.repository.user;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserRepository {

    User add(User user);

    void update(User user);

    Optional<User> findUserByLoginAndPwd(String login, String password);

    Optional<User> findUserById(int id);

    boolean findUserByLogin(String login);

    void delete(User user);
}