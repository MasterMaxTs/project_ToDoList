package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    private static final Set<String> STRINGS = Set.of("index",
                                                      "items",
                                                      "formAddUser",
                                                      "addUser",
                                                      "loginPage",
                                                      "login");

    private boolean isEnds(String url) {
        return STRINGS.stream().anyMatch(url::endsWith);
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURI();
        if (isEnds(url)) {
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
