package ru.job4j.todo.service.userservice;

import ru.job4j.todo.entity.User;

import java.util.Optional;

public interface UserService {

    User add(User user);

    void update(User user);

    Optional<User> findUserByLoginAndPwd(String login, String password);

    boolean findUserByLogin(String login);
}
