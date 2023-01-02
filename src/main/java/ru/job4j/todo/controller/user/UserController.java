package ru.job4j.todo.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.todo.controller.SessionController;
import ru.job4j.todo.model.TimeZone;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.timezone.TimeZoneService;
import ru.job4j.todo.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

/**
 * Контроллер пользователей
 */
@Controller
@AllArgsConstructor
public class UserController extends SessionController {

    /**
     * Ссылки на слои сервисов
     */
    private final UserService userService;
    private final TimeZoneService timeZoneService;

    /**
     * Добавляет пользователя во все модели,
     * определённые в контроллере пользователей
     * @see ru.job4j.todo.controller.SessionController
     * @param session HttpSession
     * @return пользователя из текущей сессии
     */
    @ModelAttribute("current")
    public User getUser(HttpSession session) {
        return getUserFromSession(session);
    }

    /**
     * Предоставляет новому пользователю форму для регистрации на сайте.
     * Для отображения данных в форме:
     * добавляет в модель список часовых поясов РФ, хранимых в БД
     * добавляет в модель сообщение в случае неуспешной регистрации
     * @param user пользователь
     * @param message сообщение пользователю
     * @param model Model
     * @return вид user/add_user
     */
    @GetMapping("/formAddUser")
    public String formAddUser(@ModelAttribute User user,
                              @RequestParam(value = "msg", required = false) String message,
                              Model model) {
        model.addAttribute("msg", message);
        model.addAttribute("timezones", timeZoneService.findAll());
        return "user/add_user";
    }

    /**
     * Добавляет пользователя в БД сайта,
     * сохраняет в Http сессии пользователя под ключом,
     * добавляет в модель имя пользователя
     * @param user пользователь
     * @param req HttpServletRequest
     * @param model Model
     * @return вид user/registration_success, если сохранение пользователя
     * прошло успешно,
     * иначе возвращает вид user/add_user, если регистрационные данные,
     * введённые пользователем, некорректны,
     * либо возвращает вид error/404 в случае возникновения ошибки
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user,
                          HttpServletRequest req,
                          Model model) {
        try {
            TimeZone timeZoneInDb = timeZoneService.findById(
                    Integer.parseInt(req.getParameter("tz.id")));
            user.setTimeZone(timeZoneInDb);
            userService.add(user);
            HttpSession session = req.getSession();
            session.setAttribute("current", user);
            model.addAttribute("name", user.getName());
            return "user/registration_success";
        } catch (NoSuchElementException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error/404";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("msg", ex.getMessage());
            model.addAttribute("timezones", timeZoneService.findAll());
            return "user/add_user";
        }
    }

    /**
     * Предоставляет пользователю форму для обновления регистрационных данных.
     * Для отображения данных в форме:
     * добавляет в модель список часовых поясов РФ, хранимых в БД,
     * добавляет в модель сообщение, если новые регистрационные данные,
     * введённые пользователем, некорректны
     * @param message сообщение пользователю
     * @param model Model
     * @return вид user/update_user
     */
    @GetMapping("/formUpdateUser")
    public String formGetUpdate(@RequestParam(value = "msg", required = false) String message,
                                Model model) {
        model.addAttribute("msg", message);
        model.addAttribute("timezones", timeZoneService.findAll());
        return "user/update_user";
    }

    /**
     * Обновляет регистрационные данные пользователя в БД сайта
     * @param user пользователь
     * @param model Model
     * @param ra RedirectAttributes
     * @param req HttpServletRequest
     * @return вид user/update_success, если обновление данных прошло успешно,
     * иначе перенаправляет на страницу формы для обновления регистрационных
     * данных с сообщением пользователю о некорректно введённых данных,
     * либо возвращает вид error/404 в случае ошибки
     */
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user,
                         Model model,
                         RedirectAttributes ra,
                         HttpServletRequest req) {
        try {
            TimeZone timeZoneInDb = timeZoneService.findById(
                    Integer.parseInt(req.getParameter("tz.id")));
            user.setTimeZone(timeZoneInDb);
            userService.update(user);
            HttpSession session = req.getSession();
            session.setAttribute("current", user);
            model.addAttribute("name", user.getName());
            return "user/update_success";
        } catch (NoSuchElementException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error/404";
        } catch (IllegalArgumentException ex) {
            ra.addAttribute("msg", ex.getMessage());
            return "redirect:/formUpdateUser";
        }
    }

    /**
     * Удаляет пользователя из БД сайта по id (опция администратора сайта)
     * @param id id пользователя
     * @param model Model
     * @return перенаправление на страницу со всеми задачами пользователей
     * /itemsOfUsers, если удаление прошло успешно,
     * иначе возвращает вид error/404 в случае ошибки
     */
    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        try {
            User userInDb = userService.findUserById(id);
            userService.delete(userInDb);
            return "redirect:/itemsOfUsers";
        } catch (NoSuchElementException ex) {
            String msg = String.format(
                    "Удаление пользователя с id = %d завершилось с ошибкой:"
                + ex.getMessage(), id);
            model.addAttribute("errorMessage", msg);
            return "error/404";
        }
    }

    /**
     * Предоставляет пользователю форму для аутентификации на сайте.
     * Добавляет в модель сообщение в случае некорретно введённых
     * регистрационных данных в виде login и password
     * @param message сообщение пользователю
     * @param model Model
     * @return вид user/login_user
     */
    @GetMapping("/loginPage")
    public String formGetLoginPage(@RequestParam(value = "msg", required = false) String message,
                                   Model model) {
        model.addAttribute("msg", message);
        return "user/login_user";
    }

    /**
     * Выполняет процедуру аутентификации пользователя на сайте,
     * сохраняет в Http сессии найденного в БД пользователя под ключом
     * @param req HttpServletRequest
     * @param ra RedirectAttributes
     * @return перенаправление на страницу со всеми задачами пользователя
     * /index, если аутентификация прошла успешно,
     * иначе перенаправление на страницу с формой для аутентификации на сайте
     * с сообщением пользователю о некорретно введённых данных
     */
    @PostMapping("/login")
    public String login(HttpServletRequest req, RedirectAttributes ra) {
        try {
            User userInDb = userService.findUserByLoginAndPwd(
                    req.getParameter("login"), req.getParameter("password")
            );
            HttpSession session = req.getSession();
            session.setAttribute("current", userInDb);
            return "redirect:/index";
        } catch (NoSuchElementException ex) {
            String message = "Введите login и password повторно или "
                    + "зарегистрируйтесь в приложении!";
            ra.addAttribute("msg", ex.getMessage() + message);
            return "redirect:/loginPage";
        }
    }

    /**
     * Выполняет процедуру завершения сеанса пользователя
     * @param session HttpSession
     * @return перенаправление на форму аутентификации
     * /loginPage
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
