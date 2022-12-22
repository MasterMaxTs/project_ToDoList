package ru.job4j.todo.repository.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.crud.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Реализация хранилища задач
 */
@Repository
@AllArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    /**
     * Делегирование выполнения CRUD-операций
     * @see ru.job4j.todo.repository.crud.CrudRepositoryImpl
     */
    private final CrudRepository crudRepository;

    /**
     * Сохраняет или обновляет задачу в базе данных.
     * @param item задача.
     * @return задача с пронициализированным id.
     */
    @Override
    public Item create(Item item) {
        crudRepository.run(session -> session.saveOrUpdate(item));
        return item;
    }

    /**
     * Список всех задач, отсортированных по приоритету и дате создания
     * от ранних к поздним.
     * @return список всех задач пользователей сайта.
     */
    @Override
    public List<Item> findAll() {
        return crudRepository.query(
                "from Item order by priority asc, created desc",
                Item.class
        );
    }

    /**
     * @return список задач, отсортированных по приоритету и дате создания
     * от ранних к поздним.
     */
    @Override
    public List<Item> findAll(User user) {
        return crudRepository.query(
                "from Item where user = :fUser order by "
                        + "priority asc, created desc",
                Item.class,
                Map.of("fUser", user)
        );
    }

    /**
     * @return список завершенных задач, отсортированных по приоритету и дате
     * создания от ранних к поздним.
     */
    @Override
    public List<Item> findCompleted(User user) {
        return crudRepository.query(
                "from Item where user = :fUser AND done = :isDone"
                        + " order by priority asc, created desc",
                Item.class,
                Map.of("fUser", user, "isDone", true)
        );
    }

    /**
     * @return список текущих (незавершенных) задач, отсортированных по
     * приоритету и дате создания от ранних к поздним.
     */
    @Override
    public List<Item> findNew(User user) {
        return crudRepository.query(
                "from Item where user = :fUser AND done = :isDone"
                + " order by priority asc, created desc",
                Item.class,
                Map.of("fUser", user, "isDone", false)
        );
    }

    @Override
    public void update(Item item) {
        crudRepository.run(session -> session.merge(item));
    }

    @Override
    public void delete(int id, User user) {
        crudRepository.run(
                "delete from Item where id = :fId AND user = :fUser",
                Map.of("fId", id, "fUser", user)
        );
    }

    @Override
    public Item findById(int id, User user) {
        return crudRepository.optional(
                "from Item where id = :fId AND user = :fUser", Item.class,
                Map.of("fId", id, "fUser", user)
        ).orElseThrow(
                () -> new NoSuchElementException(
                       String.format("задачи с id = %d не найдено в БД", id)
                ));
    }
}
