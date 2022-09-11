package ru.job4j.todo.controller;

import ru.job4j.todo.entity.User;

import javax.servlet.http.HttpSession;

public interface ManageSession {

    default User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("current");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
