package ru.job4j.todo.controller.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.todo.controller.UserController;
import ru.job4j.todo.model.TimeZone;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.timezone.TimeZoneService;
import ru.job4j.todo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Класс используется для выполнения модульных тестов
 * слоя контроллера пользователей
 */
public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private TimeZoneService timeZoneService;
    private HttpServletRequest request;
    private HttpSession session;
    private RedirectAttributes ra;
    private Model model;
    private User user;
    private TimeZone userTimeZone;

    /**
     * Инициализация объекта контроллера пользователей,
     * инициализвация начальных данных до выполнения тестов
     */
    @Before
    public void whenSetUp() {
        userService = mock(UserService.class);
        timeZoneService = mock(TimeZoneService.class);
        userController = new UserController(userService, timeZoneService);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        ra = mock(RedirectAttributes.class);
        model = mock(Model.class);
        user = new User("userName", "userLogin", "userPass");
        userTimeZone = new TimeZone("Europe/Moscow", "+3");
        userTimeZone.setId(2);
        user.setTimeZone(userTimeZone);
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * и корректного сопоставления вида для формы регистрации
     * нового пользователя
     */
    @Test
    public void whenGetFormForAddNewUserThenVerifyDataInModelAndReturnViewName() {
        String page = userController.formAddUser(new User(), " ", model);
        List<TimeZone> timeZones = List.of();
        doReturn(timeZones).when(timeZoneService).findAll();
        assertThat(page, is("user/add_user"));
        verify(model).addAttribute("timezones", timeZones);
    }

    /**
     * Тест проверяет сценарий отработки метода по созданию нового пользователя,
     * когда введены корректные регистрационные данные,
     * и сопоставления вида
     */
    @Test
    public void whenAddNewUserWithCorrectRegisterDataThenVerifyDataAndReturnViewName() {
        int tzId = userTimeZone.getId();
        doReturn(String.valueOf(userTimeZone.getId()))
                .when(request)
                .getParameter("tz.id");
        doReturn(userTimeZone).when(timeZoneService).findById(tzId);
        doReturn(session).when(request).getSession();
        String page = userController.addUser(user, request, model);
        assertThat(page, is("user/registration_success"));
        verify(model).addAttribute("name", user.getName());
    }

    /**
     * Тест проверяет сценарий отработки метода по созданию нового пользователя,
     * когда регистрационные данные введены некорректно,
     * и сопоставления вида
     */
    @Test
    public void whenAddNewUserWithIncorrectRegistrationDataThenVerifyDataAndReturnViewName() {
        int tzId = userTimeZone.getId();
        Exception exception = new IllegalArgumentException("Your registration"
                + " data is wrong!");
        doReturn(String.valueOf(tzId)).when(request).getParameter("tz.id");
        doReturn(userTimeZone).when(timeZoneService).findById(tzId);
        doThrow(exception).when(userService).add(user);
        String page = userController.addUser(user, request, model);
        assertThat(page, is("user/add_user"));
        verify(model).addAttribute("msg", exception.getMessage());
    }

    /**
     * Тест проверяет сценарий отработки метода по созданию нового пользователя,
     * когда произошла ошибка в нахождении часового пояса по id, и
     * сопоставления вида
     */
    @Test
    public void whenGetErrorPageForAddNewUserDueToInvalidTimeZoneIdThenVerifyDataInModelAndReturnViewName() {
        int tzId = 10;
        Exception exception = new NoSuchElementException("TimeZone is not "
                + "found in DB!");
        doReturn(String.valueOf(tzId)).when(request).getParameter("tz.id");
        doThrow(exception).when(timeZoneService).findById(tzId);
        String page = userController.addUser(user, request, model);
        assertThat(page, is("error/404"));
        verify(model).addAttribute("errorMessage", exception.getMessage());
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * и корректного сопоставления вида для формы обновления
     * регистрационных данных пользователя
     */
    @Test
    public void whenGetFormForUpdateUserThenVerifyDataInModelAndReturnViewName() {
        String page = userController.formGetUpdate(" ", model);
        List<TimeZone> timeZones = List.of();
        doReturn(timeZones).when(timeZoneService).findAll();
        assertThat(page, is("user/update_user"));
        verify(model).addAttribute("timezones", timeZones);
    }

    /**
     * Тест проверяет сценарий отработки метода по обновлению
     * данных пользователя, когда обновляемые данные введены корректно,
     * и сопоставления вида
     */
    @Test
    public void whenUpdateUserThenVerifyDataAndReturnViewName() {
        int newTzId = 5;
        TimeZone newUserTimeZone = new TimeZone();
        doReturn(String.valueOf(newTzId)).when(request).getParameter("tz.id");
        doReturn(newUserTimeZone).when(timeZoneService).findById(newTzId);
        doReturn(session).when(request).getSession();
        String page = userController.updateUser(user, model, ra, request);
        assertThat(page, is("user/update_success"));
        verify(model).addAttribute("name", user.getName());
    }

    /**
     * Тест проверяет сценарий отработки метода по обновлению
     * данных пользователя, когда обновляемые данные введены некорректно,
     * и сопоставления вида
     */
    @Test
    public void whenUpdateUserWithIncorrectRegistrationDataThenVerifyDataAndReturnViewName() {
        int tzId = userTimeZone.getId();
        Exception exception = new IllegalArgumentException("Your registration"
                + " data is wrong!");
        doReturn(String.valueOf(tzId)).when(request).getParameter("tz.id");
        doThrow(exception).when(userService).update(user);
        String page = userController.updateUser(user, model, ra, request);
        assertThat(page, is("redirect:/formUpdateUser"));
        verify(ra).addAttribute("msg", exception.getMessage());
    }

    /**
     * Тест проверяет сценарий отработки метода по обновлению
     * данных пользователя, когда произошла ошибка в нахождении часового
     * пояса по id, и сопоставления вида
     */
    @Test
    public void whenGetErrorPageForUpdateUserDueToInvalidTimeZoneIdThenVerifyDataInModelAndReturnViewName() {
        int tzId = 10;
        Exception exception = new NoSuchElementException("TimeZone is not "
                + "found in DB!");
        doReturn(String.valueOf(tzId)).when(request).getParameter("tz.id");
        doThrow(exception).when(timeZoneService).findById(tzId);
        String page = userController.updateUser(user, model, ra, request);
        assertThat(page, is("error/404"));
        verify(model).addAttribute("errorMessage", exception.getMessage());
    }

    /**
     * Тест проверяет сценарий отработки метода по удалению пользователя
     * и корректного сопоставления вида
     */
    @Test
    public void whenDeleteUserThenVerifyDataAndReturnViewName() {
        int id = user.getId();
        doReturn(user).when(userService).findUserById(id);
        String page = userController.deleteUser(id, model);
        assertThat(page, is("redirect:/itemsOfUsers"));
    }

    /**
     * Тест проверяет сценарий отработки метода по удалению пользователя,
     * когда произошла ошибка в нахождении пользователя по id,
     * и корректного сопоставления вида
     */
    @Test
    public void whenGetErrorPageForDeleteUserDueToInvalidUserIdThenVerifyDataInModelAndReturnViewName() {
        int id = user.getId() + 1;
        Exception exception = new NoSuchElementException("User is not "
                + "found in DB!");
        doThrow(exception).when(userService).findUserById(id);
        String page = userController.deleteUser(id, model);
        assertThat(page, is("error/404"));
    }

    /**
     * Тест проверяет сценарий корректной вставки данных
     * и корректного сопоставления вида для формы аутентификации
     * пользователя на сайте
     */
    @Test
    public void whenGetFormForAuthenticationUserThenVerifyDataInModelAndReturnViewName() {
        String page = userController.formGetLoginPage(" ", model);
        assertThat(page, is("user/login_user"));
    }

    /**
     * Тест проверяет сценарий отработки метода успешной аутентификации
     * пользователя на сайте,
     * и корректного сопоставления вида
     */
    @Test
    public void whenAuthenticateUserThenVerifyDataAndReturnViewName() {
        String login = user.getLogin();
        String password = user.getPassword();
        doReturn(login).when(request).getParameter("login");
        doReturn(password).when(request).getParameter("password");
        doReturn(user).when(userService).findUserByLoginAndPwd(login, password);
        doReturn(session).when(request).getSession();
        String page = userController.login(request, ra);
        assertThat(page, is("redirect:/index"));
    }

    /**
     * Тест проверяет сценарий отработки метода аутентификации
     * пользователя на сайте, когда пользовательские данные введены некорректно,
     * и сопоставления вида
     */
    @Test
    public void whenGetLoginPageForAuthenticateUserDueToInvalidUserDataThenVerifyDataAndReturnViewName() {
        String login = user.getLogin();
        String password = "invalidPass";
        Exception exception = new NoSuchElementException("User is not found!");
        doReturn(login).when(request).getParameter("login");
        doReturn(password).when(request).getParameter("password");
        doThrow(exception)
                .when(userService)
                .findUserByLoginAndPwd(login, password);
        String page = userController.login(request, ra);
        assertThat(page, is("redirect:/loginPage"));
    }
}