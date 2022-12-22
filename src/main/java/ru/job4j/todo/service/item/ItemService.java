package ru.job4j.todo.service.item;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис задач
 */
public interface ItemService {

    /**
     * Выполняет сохранение задачи.
     * @param item сохраняемая задача пользователя
     * @return задачу с проинициализированным id
     */
    Item create(Item item);

    /**
     * Возвращает список задач пользователей
     * @return список всех задач пользователей
     */
    List<Item> findAll();

    /**
     * Возвращает список задач конкретного пользователя
     * @return список всех задач пользователя
     */
    List<Item> findAll(User user);

    /**
     * Возвращает список завершённых задач конкретного пользователя
     * @return список всех завершённых задач пользователя
     */
    List<Item> findCompleted(User user);

    /**
     * Возвращает список текущих задач конкретного пользователя
     * @return список всех незавершённых задач пользователя
     */
    List<Item> findNew(User user);

    /**
     * Выполняет обновление данных задачи
     * @param item обновляемая задача
     */
    void update(Item item);

    /**
     * Выполняет удаление задачи пользователя
     * @param id id задачи
     * @param user пользователь
     */
    void delete(int id, User user);

    /**
     * Находит задачу по ID, принадлежащую пользователю.
     * @param id id задачи,
     * @param user пользователь
     * @return задачу, если она найдена, иначе пробрасывает исключение
     */
    Item findById(int id, User user) throws NoSuchElementException;
}
