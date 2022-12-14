package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.todo.entity.TimeZone;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.service.timezoneservice.TimeZoneService;
import ru.job4j.todo.service.userservice.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController implements ManageSession {

    private final UserService userService;
    private final TimeZoneService timeZoneService;

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
        if (!userService.findUserByLogin(user.getLogin())) {
            Optional<TimeZone> timeZoneInDb = timeZoneService.findById(
                    Integer.parseInt(req.getParameter("tz.id")));
            timeZoneInDb.ifPresent(user::setTimeZone);
            userService.add(user);
            HttpSession session = req.getSession();
            session.setAttribute("current", user);
            model.addAttribute("name", user.getName());
            return "user/login_success";
        }
        String message = String.format(
                "Уважаемый/ая, %s! Пользователь с login = %s уже существует! "
                        + "Придумайте другой login!", user.getName(), user.getLogin()
        );
        model.addAttribute("msg", message);
        return "user/add_user";
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
        String name = user.getName();
        String login = user.getLogin();
        User current = (User) model.getAttribute("current");
        String currentLogin = current.getLogin();
        if (userService.findUserByLogin(login) && !login.equals(currentLogin)) {
            String message = String.format(
                    "Уважаемый/ая, %s! Пользователь с login = %s уже "
                            + "существует! Придумайте другой login!", name, login
            );
            ra.addAttribute("msg", message);
            return "redirect:/formUpdateUser";
        }
        Optional<TimeZone> timeZoneInDb = timeZoneService.findById(
                Integer.parseInt(req.getParameter("tz.id")));
        timeZoneInDb.ifPresent(user::setTimeZone);
        userService.update(user);
        HttpSession session = req.getSession();
        session.setAttribute("current", user);

        return "redirect:/index";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        Optional<User> userInDb = userService.findUserById(id);
        userInDb.ifPresent(userService::delete);
        return "redirect:/itemsOfUsers";
    }

    @GetMapping("/loginPage")
    public String formGetLoginPage(@RequestParam(value = "msg", required = false) String message,
                                   Model model) {
        model.addAttribute("msg", message);
        return "user/login_user";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, RedirectAttributes ra) {
        Optional<User> userInDb = userService.findUserByLoginAndPwd(
                req.getParameter("login"), req.getParameter("password")
        );
        if (userInDb.isEmpty()) {
            String message = "Некорректный ввод login или password!";
            ra.addAttribute("msg", message);
            return "redirect:/loginPage";
        }
        HttpSession session = req.getSession();
        session.setAttribute("current", userInDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
