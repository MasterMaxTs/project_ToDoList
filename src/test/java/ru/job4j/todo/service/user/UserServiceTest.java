package ru.job4j.todo.service.user;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.user.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Класс используется для выполнения модульных тестов
 * слоя сервисов пользователей
 */
public class UserServiceTest {

    private UserRepository store;
    private UserService service;
    private User user;

    /**
     * Инициализация сервиса пользователей,
     * инициализация объекта пользователя до выполнения тестов
     */
    @Before
    public void whenSetUp() {
        store = mock(UserRepository.class);
        service = new UserServiceImpl(store);
        user = new User();
        user.setName("userName");
        user.setLogin("userLogin");
    }

    /**
     * Тест проверяет сценарий корректной регистрации нового пользователя
     */
    @Test
    public void whenAddNewUserWithCorrectRegistrationDataThanSuccess() {
        doReturn(false).when(store).findUserByLogin(user.getLogin());
        service.add(user);
    }

    /**
     * Тест проверяет сценарий регистрации нового пользователя c
     * недопустимым login с выбросом соответствующего исключения
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenAddNewUserWithInCorrectLoginThanException() {
        doReturn(true).when(store).findUserByLogin(user.getLogin());
        service.add(user);
    }

    /**
     * Тест проверяет сценарий регистрации нового пользователя c
     * недопустимым именем с выбросом соответствующего исключения
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenAddNewUserWithInCorrectUserNameThanException() {
        user.setName("Гость");
        service.add(user);
    }

    /**
     * Тест проверяет сценарий обновления данных о пользователе,
     * когда регистрационные данные изменены корректно
     */
    @Test
    public void whenUpdateUserWithCorrectRegistrationDataThanSuccess() {
        doReturn(user).when(store).findUserById(any(Integer.class));
        user.setName("newUserName");
        service.update(user);
    }

    /**
     * Тест проверяет сценарий ошибки в обновлении данных о пользователе
     * с выбросом исключения, когда имя пользователя изменено некорректно
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenUpdateUserWithIncorrectUserNameThanException() {
        doReturn(user).when(store).findUserById(any(Integer.class));
        user.setName("Гость");
        service.update(user);
    }

    /**
     * Тест проверяет сценарий ошибки в обновлении данных о пользователе
     * с выбросом исключения, когда login пользователя изменён некорректно
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenUpdateUserWithIncorrectLoginThanException() {
        User current = new User();
        current.setName(user.getName());
        current.setLogin(user.getLogin());
        user.setLogin("newUserLogin");
        doReturn(current).when(store).findUserById(any(Integer.class));
        doReturn(true).when(store).findUserByLogin(user.getLogin());
        service.update(user);
    }
}