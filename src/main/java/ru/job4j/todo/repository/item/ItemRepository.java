package ru.job4j.todo.repository.item;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Хранилище задач пользователя
 */
public interface ItemRepository {

    /**
     * Выполняет сохранение задачи в базу данных.
     * При успешном сохранении возвращает задачу,
     * у которой проинициализирован id.
     * @param item сохраняемая задача пользователя
     * @return задачу с проинициализированным id
     */
    Item create(Item item);

    /**
     * Возвращает список всех задач пользователей, находящихся в базе данных
     * @return список всех задач пользователей
     */
    List<Item> findAll();

    /**
     * Возвращает список всех задач, находящихся в базе данных
     * и принадлежащих пользователю
     * @return список всех задач пользователя
     */
    List<Item> findAll(User user);

    /**
     * Возвращает список всех завершённых задач, находящихся в базе данных
     * и принадлежащих пользователю
     * @return список всех завершённых задач пользователя
     */
    List<Item> findCompleted(User user);

    /**
     * Возвращает список всех текущих (незавершённых) задач, находящихся в базе
     * данных и принадлежащих пользователю
     * @return список всех текущих задач пользователя
     */
    List<Item> findNew(User user);

    /**
     * Выполняет обновление задачи в базе данных
     * @param item обновляемая задача
     */
    void update(Item item);

    /**
     * Выполняет удаление задачи пользователя из базы данных
     * @param id id задачи
     * @param user пользователь
     */
    void delete(int id, User user);

    /**
     * Находит задачу в базе данных по ID, принадлежащую пользователю.
     * @param id id задачи,
     * @param user пользователь
     * @return задачу, если она найдена в базе данных,
     * иначе выбрасывает исключение
     */
    Item findById(int id, User user) throws NoSuchElementException;
}
