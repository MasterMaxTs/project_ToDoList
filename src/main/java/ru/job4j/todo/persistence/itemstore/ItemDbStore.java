package ru.job4j.todo.persistence.itemstore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.Item;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.persistence.crudrepository.CrudRepository;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class ItemDbStore implements ItemStore {

    private final CrudRepository crudRepository;

    /**
     * Сохранить задачу в базе.
     * @param item пользователь.
     * @return задача с id.
     */
    @Override
    public Item create(Item item) {
        crudRepository.run(session -> session.save(item));
        return item;
    }

    /**
     * Список задач, отсортированных по дате создания от ранних к поздним.
     * @return список задач.
     */
    @Override
    public List<Item> findAll() {
        return crudRepository.query("from Item order by created desc", Item.class);
    }

    /**
     * Список задач, отсортированных по id.
     * @param user текущий пользователь.
     * @return список задач.
     */
    @Override
    public List<Item> findAll(User user) {
        return crudRepository.query(
                "from Item where user = :fUser",
                Item.class,
                Map.of("fUser", user)
        );
    }

    /**
     * Список завершенных задач, отсортированных по id.
     * @param user текущий пользователь.
     * @return список завершенных задач.
     */
    @Override
    public List<Item> findCompleted(User user) {
        return crudRepository.query(
                "from Item where user = :fUser AND done = :isDone", Item.class,
                Map.of("fUser", user, "isDone", true)
        );
    }

    /**
     * Список новых задач, отсортированных по id.
     * @param user текущий пользователь.
     * @return список новых задач.
     */
    @Override
    public List<Item> findNew(User user) {
        return crudRepository.query(
                "from Item where user = :fUser AND done = :isDone", Item.class,
                Map.of("fUser", user, "isDone", false)
        );
    }

    /**
     * Обновить в базе задачу
     * @param item задача
     */
    @Override
    public void update(Item item) {
        crudRepository.run(session -> session.merge(item));
    }

    /**
     * удалить в базе задачу
     * @param id id задачи,
     * @param user текущий пользователь
     */
    @Override
    public void delete(int id, User user) {
        crudRepository.run(
                "delete from Item where id = :fId AND user = :fUser",
                Map.of("fId", id, "fUser", user)
        );
    }

    /**
     * найти в базе задачу по ID.
     * @param id id задачи,
     * @param user текущий пользователь
     */
    @Override
    public Item findById(int id, User user) {
        return crudRepository.optional(
                "from Item where id = :fId AND user = :fUser", Item.class,
                Map.of("fId", id, "fUser", user)
        ).orElseThrow(
                () -> new NullPointerException(
                       String.format("задачи с id = %d не найдено в БД", id)
                ));
    }
}
