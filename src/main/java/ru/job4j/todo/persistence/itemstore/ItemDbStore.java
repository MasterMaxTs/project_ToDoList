package ru.job4j.todo.persistence.itemstore;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.Item;
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
        crudRepository.run(session -> session.persist(item));
        return item;
    }

    /**
     * Список задач, отсортированных по id.
     * @param userId id текущего пользователя.
     * @return список задач.
     */
    @Override
    public List<Item> findAll(int userId) {
        return crudRepository.query(
                "from Item where userId = :fId", Item.class, Map.of("fId",
                        userId)
        );
    }

    /**
     * Список завершенных задач, отсортированных по id.
     * @param userId id текущего пользователя.
     * @return список завершенных задач.
     */
    public List<Item> findCompleted(int userId) {
        return crudRepository.query(
                "from Item where userId = :fId AND done = :isDone", Item.class,
                Map.of("fId", userId, "isDone", true)
        );
    }

    /**
     * Список новых задач, отсортированных по id.
     * @param userId id текущего пользователя.
     * @return список новых задач.
     */
    public List<Item> findNew(int userId) {
        return crudRepository.query(
                "from Item where userId = :fId AND done = :isDone", Item.class,
                Map.of("fId", userId, "isDone", false)
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
     * @param userId id текущего пользователя
     */
    @Override
    public void delete(int id, int userId) {
        crudRepository.run(
                "delete from Item where id = :fId AND userId = :fUid",
                Map.of("fId", id, "fUid", userId)
        );
    }

    /**
     * найти в базе задачу по ID.
     * @param id id задачи,
     * @param userId id текущего пользователя
     */
    @Override
    public Item findById(int id, int userId) {
        return crudRepository.optional(
                "from Item where id = :fId AND userId = :fUid", Item.class,
                Map.of("fId", id, "fUid", userId)
        ).orElseThrow(
                () -> new NullPointerException(
                       String.format("задачи с id = %d не найдено в БД", id)
                ));
    }
}
