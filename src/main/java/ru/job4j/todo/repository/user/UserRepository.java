package ru.job4j.todo.repository.user;

import ru.job4j.todo.model.User;

/**
 * Хранилище пользователей
 */
public interface UserRepository {

    /**
     * Добавляет пользователя в базу данных.
     * @param user пользователь.
     * @return пользователя с проинициализированным id.
     */
    User add(User user);

    /**
     * Обновляет пользователя в базе данных
     * @param user пользователь
     */
    void update(User user);

    /**
     * Находит пользователя в базе данных по login и password.
     * @param login login пользователя.
     * @param password password пользователя.
     * @return пользователя, если он найден,
     * иначе выбрасывает исключение
     */
    User findUserByLoginAndPwd(String login, String password);

    /**
     * Находит пользователя в базе данных по ID.
     * @param id пользователя.
     * @return пользователя, если он найден,
     * иначе выбрасывает исключение
     */
    User findUserById(int id);

    /**
     * Проверяет наличие пользователя в базе данных по login.
     * @param login login пользователя.
     * @return true, если пользователь с указанным логин найден в базе данных,
     * иначе false
     */
    boolean findUserByLogin(String login);

    /**
     * Удаляет пользователя из базы данных.
     * @param user пользователь.
     */
    void delete(User user);
}
