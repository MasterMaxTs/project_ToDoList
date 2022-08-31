package ru.job4j.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.todo.entity.User;
import ru.job4j.todo.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController implements ManageSession {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ModelAttribute("user")
    public User getUser(HttpSession session) {
        return getUserFromSession(session);
    }

    @GetMapping("/formAddUser")
    public String formAddUser(Model model,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "msg", required = false) String message) {
        model.addAttribute("name", name);
        model.addAttribute("msg", message);
        return "user/add_user";
    }

    @PostMapping("/addUser")
    public String addUser(HttpServletRequest req,
                          RedirectAttributes ra,
                          Model model) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (!service.findUserByLogin(login)) {
            User user = new User(name, login, password);
            service.add(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            model.addAttribute("name", user.getName());
            return "user/login_success";
        }
        String message = String.format(
                "Уважаемый, %s! Пользователь с login = %s уже существует! "
                        + "Придумайте другой login!", name, login
        );
        ra.addAttribute("name", name);
        ra.addAttribute("msg", message);
        return "redirect:/formAddUser";
    }

    @GetMapping("/formUpdateUser")
    public String formGetUpdate() {
        return "user/update_user";
    }

    @PostMapping("/updateUser")
    public String update(@ModelAttribute User user, Model model) {
        User current = (User) model.getAttribute("user");
        user.setId(current.getId());
        service.update(user);
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
        Optional<User> userInDb = service.findUserByLoginAndPwd(
                req.getParameter("login"), req.getParameter("password")
        );
        if (userInDb.isEmpty()) {
            String message = "Некорректный ввод login или password!";
            ra.addAttribute("msg", message);
            return "redirect:/loginPage";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userInDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
