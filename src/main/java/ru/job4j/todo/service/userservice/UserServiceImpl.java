package ru.job4j.todo.service.userservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.persistence.userstore.UserStore;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStore store;

    @Override
    public User add(User user) {
        return store.add(user);
    }

    @Override
    public boolean findUserByLogin(String login) {
        return store.findUserByLogin(login);
    }

    @Override
    public Optional<User> findUserByLoginAndPwd(String login, String password) {
        return store.findUserByLoginAndPwd(login, password);
    }

    @Override
    public void update(User user) {
        store.update(user);
    }
}
