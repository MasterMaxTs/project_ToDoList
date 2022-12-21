package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить пользователя в базе.
     * @param user пользователь.
     * @return задача с id.
     */
    @Override
    public User add(User user) {
        crudRepository.run(session -> session.save(user));
        return user;
    }

    /**
     * Проверить наличие пользователя в базе по login.
     * @param login login пользователя.
     * @return boolean
     */
    @Override
    public boolean findUserByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :fLogin", User.class,
                Map.of("fLogin", login)
        ).isPresent();
    }

    /**
     * Удалить пользователя в базе.
     * @param user пользователь.
     */
    @Override
    public void delete(User user) {
        crudRepository.run(session -> session.delete(user));
    }

    /**
     * Найти пользователя в базе по login и password.
     * @param login login пользователя.
     * @param password password пользователя.
     * @return Optional<User>
     */
    @Override
    public Optional<User> findUserByLoginAndPwd(String login, String password) {
        return crudRepository.optional(
                "from User where login = :fLogin AND password = :fPwd",
                User.class,
                Map.of("fLogin", login, "fPwd", password)
        );
    }

    /**
     * Найти пользователя в базе по id.
     * @param id пользователя.
     * @return Optional<User>
     */
    @Override
    public Optional<User> findUserById(int id) {
        return crudRepository.optional(
                "from User where id = :fId",
                User.class,
                Map.of("fId", id)
        );
    }

    /**
     * Обновить в базе пользователя
     * @param user пользователь
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }
}
