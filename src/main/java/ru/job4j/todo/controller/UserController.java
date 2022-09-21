package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.service.userservice.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController implements ManageSession {

    private final UserService userService;

    @ModelAttribute("current")
    public User getUser(HttpSession session) {
        return getUserFromSession(session);
    }

    @GetMapping("/formAddUser")
    public String formAddUser(@ModelAttribute User user,
                              @RequestParam(value = "msg", required = false) String message,
                              Model model) {
        model.addAttribute("msg", message);
        return "user/add_user";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user,
                          HttpServletRequest req,
                          Model model) {
        if (!userService.findUserByLogin(user.getLogin())) {
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
        return "user/update_user";
    }

    @PostMapping("/updateUser")
    public String update(@ModelAttribute User user,
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
        userService.update(user);
        HttpSession session = req.getSession();
        session.setAttribute("current", user);

        return "redirect:/index";
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
