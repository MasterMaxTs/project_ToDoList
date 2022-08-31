package ru.job4j.todo.controllers;

import ru.job4j.todo.entity.User;

import javax.servlet.http.HttpSession;

public interface ManageSession {

    default User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
