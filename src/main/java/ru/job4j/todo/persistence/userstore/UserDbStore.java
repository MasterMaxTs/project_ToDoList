package ru.job4j.todo.persistence.userstore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.persistence.crudrepository.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDbStore implements UserStore {

    private final CrudRepository crudRepository;

    /**
     * Сохранить пользователя в базе.
     * @param user пользователь.
     * @return задача с id.
     */
    @Override
    public User add(User user) {
        crudRepository.run(session -> session.persist(user));
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
     * Обновить в базе пользователя
     * @param user пользователь
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }
}
