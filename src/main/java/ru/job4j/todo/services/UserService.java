package ru.job4j.todo.services;

import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.persistence.UserStoreImpl;

import java.util.Optional;

@Service
public class UserService {

    private final UserStoreImpl store;

    public UserService(UserStoreImpl store) {
        this.store = store;
    }

    public User add(User user) {
        return store.add(user);
    }

    public boolean findUserByLogin(String login) {
        return store.findUserByLogin(login);
    }

    public Optional<User> findUserByLoginAndPwd(String login, String password) {
        return store.findUserByLoginAndPwd(login, password);
    }

    public boolean update(User user) {
        return store.update(user);
    }
}
