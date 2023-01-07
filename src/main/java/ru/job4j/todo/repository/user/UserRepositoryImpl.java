package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Реализация хранилища пользователей
 */
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    /**
     * Делегирование выполнения CRUD-операций
     * @see ru.job4j.todo.repository.crud.CrudRepositoryImpl
     */
    private final CrudRepository crudRepository;

    @Override
    public User add(User user) {
        crudRepository.run(session -> session.save(user));
        return user;
    }

    @Override
    public boolean findUserByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :fLogin", User.class,
                Map.of("fLogin", login)
        ).isPresent();
    }

    @Override
    public void delete(User user) {
        crudRepository.run(session -> session.delete(user));
    }

    @Override
    public void deleteAll() {
        crudRepository.run("delete from User", Map.of());
    }

    @Override
    public User findUserByLoginAndPwd(String login, String password) {
        return crudRepository.optional(
                "from User where login = :fLogin AND password = :fPwd",
                User.class,
                Map.of("fLogin", login, "fPwd", password)
        ).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Пользователь с login = %s"
                                + " и password = %s не найден в БД! ",
                                login, password)
                ));
    }

    @Override
    public User findUserById(int id) {
        return crudRepository.optional(
                "from User where id = :fId",
                User.class,
                Map.of("fId", id)
        ).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Пользователь с id = %d"
                                        + " не найден в БД! ", id)
                ));
    }

    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }
}
