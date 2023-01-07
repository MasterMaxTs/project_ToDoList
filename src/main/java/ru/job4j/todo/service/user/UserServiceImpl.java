package ru.job4j.todo.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.user.UserRepository;

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
        String userName = user.getName();
        boolean find = store.findUserByLogin(user.getLogin());
        if (userName.equals("Гость")) {
            throw new IllegalArgumentException(
                            "Уважаемый пользователь! Введённое вами имя "
                                    + "'Гость' не может быть использовано в "
                                    + "приложении! Придумайте другое имя!"
                    );
        }
        if (find) {
            throw new IllegalArgumentException(
                    String.format(
                    "Уважаемый/ая, %s! Пользователь с login = %s уже существует! "
                            + "Придумайте другой login!",
                            user.getName(), user.getLogin()
            ));
        }
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
    public void deleteAll() {
        store.deleteAll();
    }

    @Override
    public User findUserByLoginAndPwd(String login, String password) {
        return store.findUserByLoginAndPwd(login, password);
    }

    @Override
    public User findUserById(int id) {
        return store.findUserById(id);
    }

    @Override
    public void update(User user) {
        User current = findUserById(user.getId());
        String currentLogin = current.getLogin();
        String userName = user.getName();
        String userLogin = user.getLogin();
        if (userName.equals("Гость")) {
            throw new IllegalArgumentException(
                    String.format(
                            "Уважаемый/ая, %s! Пользователь с именем 'Гость' "
                                    + "не может быть зарегистрирован в "
                                    + "системе! Придумайте другое имя!",
                            current.getName()
                    ));
        }
        if (findUserByLogin(userLogin) && !userLogin.equals(currentLogin)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Уважаемый/ая, %s! Пользователь с login = %s уже "
                                    + "существует! Придумайте другой login!",
                            current.getName(), userLogin
                    ));
        }
        store.update(user);
    }
}
