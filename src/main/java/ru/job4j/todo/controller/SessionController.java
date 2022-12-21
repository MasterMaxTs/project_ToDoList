package ru.job4j.todo.controller;

import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

/**
 * Абстракция контроллера сессии
 */
public abstract class SessionController {

    /**
     * Возвращает объект пользователя из активной сессии
     * @param session объект HttpSession
     * @return текущего пользователя
     */
    protected User getUserFromSession(HttpSession session) {
        String guest = "Гость";
        User user = (User) session.getAttribute("current");
        if (user == null) {
            user = new User();
            user.setName(guest);
        }
        return user;
    }
}
