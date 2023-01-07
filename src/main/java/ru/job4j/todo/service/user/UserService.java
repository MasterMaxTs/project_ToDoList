package ru.job4j.todo.service.user;

import ru.job4j.todo.model.User;

/**
 * Сервис пользователей
 */
public interface UserService {

    /**
     * Сохраняет пользователя.
     * @param user пользователь.
     * @return пользователя с проинициализированным id.
     */
    User add(User user);

    /**
     * Обновляет данные о пользователе
     * @param user пользователь
     */
    void update(User user);

    /**
     * Находит пользователя по login и password.
     * @param login login пользователя.
     * @param password password пользователя.
     * @return пользователя, если он найден,
     * иначе выбрасывает исключение
     */
    User findUserByLoginAndPwd(String login, String password);

    /**
     * Находит пользователя по ID.
     * @param id id пользователя.
     * @return пользователя, если он найден,
     * иначе выбрасывает исключение
     */
    User findUserById(int id);

    /**
     * Находит пользователя по login.
     * @param login login пользователя.
     * @return true, если пользователь с указанным логин найден,
     * иначе false
     */
    boolean findUserByLogin(String login);

    /**
     * Удаляет пользователя.
     * @param user пользователь.
     */
    void delete(User user);

    /**
     * Удаляет всех пользователей.
     */
    void deleteAll();
}
