package ru.job4j.todo.repository.item;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.todo.Job4jTodoApplication;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.crud.CrudRepository;
import ru.job4j.todo.repository.crud.CrudRepositoryImpl;
import ru.job4j.todo.repository.item.category.CategoryRepository;
import ru.job4j.todo.repository.item.category.CategoryRepositoryImpl;
import ru.job4j.todo.repository.item.priority.PriorityRepository;
import ru.job4j.todo.repository.item.priority.PriorityRepositoryImpl;
import ru.job4j.todo.repository.user.UserRepository;
import ru.job4j.todo.repository.user.UserRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Класс используется для выполнения интеграционных тестов при выявлении
 * правильного взаимодействия приложения с хранилищем задач пользователей
 */
public class ItemRepositoryTest {

    private static SessionFactory sessionFactory;
    private CrudRepository crud;
    private UserRepository userStore;
    private ItemRepository itemStore;
    private PriorityRepository priorityStore;
    private CategoryRepository categoryStore;
    private User user;
    private Item item;

    /**
     * Инициализация финального объекта SessionFactory на весь период
     * тестирования
     */
    @BeforeClass
    public static void whenInitSessionFactory() {
        sessionFactory = new Job4jTodoApplication().sf();
    }

    /**
     * Инициализация хранилищ:
     * задачи пользователей, приоритеты задач, категории задач, пользователи;
     * инициализация объекта пользователя,
     * инициализация объекта задачи пользователя до выполнения тестов
     */
    @Before
    public void whenSetUp() {
        crud = new CrudRepositoryImpl(sessionFactory);
        itemStore = new ItemRepositoryImpl(crud);
        priorityStore = new PriorityRepositoryImpl(crud);
        categoryStore = new CategoryRepositoryImpl(crud);
        userStore = new UserRepositoryImpl(crud);
        user = new User("Максим", "maxim", "max123");
        item = new Item("firstTask", "taskDescription", false);
        item.setUser(user);
    }

    /**
     * Очистка всех данных в тестируемой таблице H2Database после завершения
     * каждого теста
     */
    @After
    public void whenWipeTablesAfterTest() {
        deleteAllFromTasksTable();
        userStore.deleteAll();
    }

    /**
     * Удаляет все записи в таблице todo_tasks
     */
    private void deleteAllFromTasksTable() {
        crud.run("delete from Item", Map.of());
    }

    /**
     * Тест проверяет сценарий корректного сохранения задачи пользователя в
     * хранилище задач пользователей и правильное извлечение из него
     */
    @Test
    public void whenCreateNewItemThanItemRepositoryHasSameItem() {
        userStore.add(user);
        item.setPriority(priorityStore.findByPosition(2));
        item.addCategoryToTask(categoryStore.findById(3));
        item.addCategoryToTask(categoryStore.findById(5));
        itemStore.create(item);
        Item rsl = itemStore.findById(item.getId(), user);
        assertThat(rsl.getName(), is(item.getName()));
        assertThat(rsl.getPriority().getName(), is("обычный"));
        assertThat(rsl.getCategories().size(), is(2));
    }

    /**
     * Тест проверяет сценарий проверки нахождения задачи пользователя
     * в хранилище задач пользователей по id
     */
    @Test
    public void whenFindItemByIdThanItemRepositoryHasSameItem() {
        userStore.add(user);
        item.setPriority(priorityStore.findByPosition(3));
        item.addCategoryToTask(categoryStore.findById(4));
        itemStore.create(item);
        Item rsl = itemStore.findById(item.getId(), user);
        assertThat(rsl.getPriority().getName(), is("не срочный"));
        assertThat(rsl.getDescription(), is(item.getDescription()));
    }

    /**
     * Тест проверяет сценарий корректного нахождения всех задач пользователей
     * в хранилище задач
     */
    @Test
    public void whenTwoUsersCreateSomeItemsThanGetAllItemsFromItemRepository() {
        userStore.add(user);
        item.setPriority(priorityStore.findByPosition(2));
        item.addCategoryToTask(categoryStore.findById(2));
        itemStore.create(item);
        User newUser = new User("Oleg", "oleg", "olg123");
        userStore.add(newUser);
        Item newItem = new Item("secondTask", "taskDescription", false);
        newItem.setUser(newUser);
        newItem.setPriority(priorityStore.findByPosition(1));
        newItem.addCategoryToTask(categoryStore.findById(1));
        itemStore.create(newItem);
        List<Item> rsl = itemStore.findAll();
        assertThat(rsl.size(), is(2));
        assertThat(rsl.get(0).getDescription(), is(newItem.getDescription()));
        assertThat(rsl.get(1).getDescription(), is(item.getDescription()));
    }

    /**
     * Тест проверяет сценарий корректного нахождения всех задач пользователя
     * в хранилище задач
     */
    @Test
    public void whenTwoUsersCreateSomeItemsThanGetAllItemsOfOneUserFromItemRepository() {
        userStore.add(user);
        itemStore.create(item);
        User newUser = new User("Oleg", "oleg", "olg123");
        userStore.add(newUser);
        Item newItem = new Item("secondTask", "taskDescription", false);
        newItem.setUser(newUser);
        itemStore.create(newItem);
        List<Item> rsl = itemStore.findAll(user);
        assertThat(rsl.size(), is(1));
        assertThat(rsl.get(0).getDescription(), is(item.getDescription()));
    }

    /**
     * Тест проверяет сценарий корректного нахождения всех
     * выполненных задач пользователя в хранилище задач
     */
    @Test
    public void whenFindAllCompletedItemsThanGetUserItems() {
        userStore.add(user);
        item.setDone(true);
        itemStore.create(item);
        List<Item> rsl = itemStore.findCompleted(user);
        assertThat(rsl.size(), is(1));
        assertThat(rsl.get(0).getDescription(), is(item.getDescription()));
    }

    /**
     * Тест проверяет сценарий корректного нахождения всех
     * новых (невыполненных) задач пользователя в хранилище задач
     */
    @Test
    public void whenFindAllNewItemsThanGetUserItems() {
        userStore.add(user);
        item.setDone(true);
        itemStore.create(item);
        Item newItem = new Item("secondTask", "taskDescription", false);
        newItem.setUser(user);
        itemStore.create(newItem);
        List<Item> rsl = itemStore.findNew(user);
        assertThat(rsl.size(), is(1));
        assertThat(rsl.get(0).getDescription(), is(newItem.getDescription()));
    }

    /**
     * Тест проверяет сценарий корректного обновления данных задачи
     * в хранилище задач пользователей
     */
    @Test
    public void whenUpdateItemThanItemRepositoryHasSameItem() {
        userStore.add(user);
        item.setPriority(priorityStore.findByPosition(3));
        item.addCategoryToTask(categoryStore.findById(4));
        itemStore.create(item);
        Item itemInDb = itemStore.findById(item.getId(), user);
        itemInDb.setDescription("newDescription");
        itemInDb.setPriority(priorityStore.findByPosition(1));
        itemStore.update(itemInDb);
        Item rsl = itemStore.findById(itemInDb.getId(), user);
        assertThat(rsl.getDescription(), is(itemInDb.getDescription()));
        assertThat(rsl.getPriority().getName(), is("срочный"));
    }

    /**
     * Тест проверяет сценарий удаления задачи
     * из хранилища задач пользователей по id
     */
    @Test(expected = NoSuchElementException.class)
    public void whenDeleteOnlyItemThanItemRepositoryHasEmpty() {
        userStore.add(user);
        itemStore.create(item);
        itemStore.delete(item.getId(), user);
        itemStore.findById(item.getId(), user);
    }
}