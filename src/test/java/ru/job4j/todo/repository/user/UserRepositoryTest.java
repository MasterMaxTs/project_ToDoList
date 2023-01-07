package ru.job4j.todo.repository.user;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.todo.Job4jTodoApplication;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.crud.CrudRepository;
import ru.job4j.todo.repository.crud.CrudRepositoryImpl;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Класс используется для выполнения интеграционных тестов при выявлении
 * правильного взаимодействия приложения с хранилищем пользователей
 */
public class UserRepositoryTest {

    private static SessionFactory sessionFactory;
    private UserRepository store;
    private User user;

    /**
     * Инициализация финального объекта SessionFactory на весь период
     * тестирования
     */
    @BeforeClass
    public static void whenInitSessionFactory() {
        sessionFactory = new Job4jTodoApplication().sf();
    }

    /**
     * Инициализация хранилища пользователей,
     * инициализация объекта пользователя до выполнения тестов
     */
    @Before
    public void whenSetUpThanInitUserRepositoryAndInitUserData() {
        CrudRepository crud = new CrudRepositoryImpl(sessionFactory);
        store = new UserRepositoryImpl(crud);
        user = new User("Максим", "max", "max123");
    }

    /**
     * Очистка всех данных в тестируемой таблице H2Database после завершения
     * каждого теста
     */
    @After
    public void whenWipeTableAfterTest() {
        store.deleteAll();
    }

    /**
     * Тест проверяет сценарий корректного сохранения одного пользователя в
     * хранилище пользователей и правильное извлечение из него по id
     * пользователя
     */
    @Test
    public void whenCreateNewUserThanUserRepositoryHasSameUser() {
        store.add(user);
        User userInDb = store.findUserById(user.getId());
        assertThat(userInDb.getLogin(), is(user.getLogin()));
    }

    /**
     * Тест проверяет сценарий проверки нахождения пользователя
     * в хранилище пользователей по его login
     */
    @Test
    public void whenFindUserByLoginThanUserRepositoryHasSameUser() {
        store.add(user);
        assertTrue(store.findUserByLogin(user.getLogin()));
        assertFalse(store.findUserByLogin("someLogin"));
    }

    /**
     * Тест проверяет сценарий корректного удаления пользователя
     * из хранилища пользователей, нахождения ранее удалённого пользователя
     * в хранилище пользователей по id с выбросом исключения
     */
    @Test(expected = NoSuchElementException.class)
    public void whenDeleteUserThanFindThisUserByIdInRepositoryThrowsException() {
        store.add(user);
        store.delete(user);
        store.findUserById(user.getId());
    }

    /**
     * Тест проверяет сценарий корректного извлечения пользователя из хранилища
     * пользователей, когда его регистрационные данные в виде login и password
     * введены корректно
     */
    @Test
    public void whenFindUserByLoginAndPasswordThanUserRepositoryHasSameUser() {
        store.add(user);
        User userInDb = store.findUserByLoginAndPwd(user.getLogin(),
                                                    user.getPassword());
        assertThat(userInDb.getLogin(), is(user.getLogin()));
    }

    /**
     * Тест проверяет сценарий, что извлечение пользователя из хранилища
     * пользователей, если его регистрационные данные в виде login и password
     * введены некорректно, выбрасывает исключение
     */
    @Test(expected = NoSuchElementException.class)
    public void whenFindUserByLoginAndPasswordThanThrownException() {
        store.add(user);
        store.findUserByLoginAndPwd("login", "pwd");
    }

    /**
     * Тест проверяет сценарий извлечения пользователя из
     * хранилища пользователей по id, если такой id существует
     */
    @Test
    public void whenFindUserByIdThanUserRepositoryHasSameUser() {
        store.add(user);
        User userInDb = store.findUserById(user.getId());
        assertThat(userInDb.getLogin(), is(user.getLogin()));
    }

    /**
     * Тест проверяет сценарий извлечения пользователя из
     * хранилища пользователей по несуществующему id с выбросом исключения
     */
    @Test(expected = NoSuchElementException.class)
    public void whenFindUserByIdThanThrownException() {
        store.add(user);
        store.findUserById(user.getId() + 1);
    }

    /**
     * Тест проверяет сценарий корректного обновления данных о пользователе
     * в хранилище пользователей
     */
    @Test
    public void whenUpdateUserDataThanUserRepositoryHasSameUser() {
        store.add(user);
        User userInDb = store.findUserById(user.getId());
        userInDb.setName("Администратор");
        store.update(userInDb);
        assertThat(
                store.findUserById(userInDb.getId()).getName(),
                is("Администратор")
        );
    }
}