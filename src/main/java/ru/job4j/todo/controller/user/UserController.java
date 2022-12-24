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

    @GetMapping("/formAddUser")
    public String formAddUser(@ModelAttribute User user,
                              @RequestParam(value = "msg", required = false) String message,
                              Model model) {
        model.addAttribute("msg", message);
        model.addAttribute("timezones", timeZoneService.findAll());
        return "user/add_user";
    }

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

    @GetMapping("/formUpdateUser")
    public String formGetUpdate(@RequestParam(value = "msg", required = false) String message,
                                Model model) {
        model.addAttribute("msg", message);
        model.addAttribute("timezones", timeZoneService.findAll());
        return "user/update_user";
    }

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

    @GetMapping("/loginPage")
    public String formGetLoginPage(@RequestParam(value = "msg", required = false) String message,
                                   Model model) {
        model.addAttribute("msg", message);
        return "user/login_user";
    }

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
