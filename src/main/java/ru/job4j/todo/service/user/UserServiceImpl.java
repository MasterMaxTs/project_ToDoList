package ru.job4j.todo.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.user.UserRepository;

import java.util.Optional;

/**
 * Реализация сервиса пользователей
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * Делегирование выполнения CRUD-операций хранилищу пользователей
     */
    private final UserRepository store;

    @Override
    public User add(User user) {
        return store.add(user);
    }

    @Override
    public boolean findUserByLogin(String login) {
        return store.findUserByLogin(login);
    }

    @Override
    public void delete(User user) {
        store.delete(user);
    }

    @Override
    public Optional<User> findUserByLoginAndPwd(String login, String password) {
        return store.findUserByLoginAndPwd(login, password);
    }

    @Override
    public Optional<User> findUserById(int id) {
        return store.findUserById(id);
    }

    @Override
    public void update(User user) {
        store.update(user);
    }
}
