package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Фильтр проверки прав доступа пользователя к контенту сайта
 */
@Component
public class AuthFilter implements Filter {

    /**
     * Совокупность уникальных окончаний строк URI, при которой пользователю,
     * не прошедшему аутентификацию на сайте, доступно содержимое
     * запрошенного контента
     */
    private static final Set<String> STRINGS = Set.of("index",
                                                      "items",
                                                      "formAddUser",
                                                      "addUser",
                                                      "loginPage",
                                                      "login");

    /**
     * Проверка соответствия запрошенного URI доступности
     * содержимого контента сайта для пользователя,
     * не прошедшего аутентификацию
     * @param uri URI
     * @return результат проверки
     */
    private boolean isEnds(String uri) {
        return STRINGS.stream().anyMatch(uri::endsWith);
    }

    /**
     * Реализация метода фильтрации
     * @param request объект запроса в виде HttpServletRequest
     * @param response объект ответа в виде HttpServletResponse
     * @param chain объект FilterChain
     */
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (isEnds(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("current") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
        }
    }
