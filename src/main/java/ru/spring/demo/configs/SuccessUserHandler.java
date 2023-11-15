package ru.spring.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.spring.demo.dao.UserDAOImpl;
import ru.spring.demo.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    private final UserServiceImpl userService;
    private final UserDAOImpl userDAO;

    @Autowired
    public SuccessUserHandler(UserServiceImpl userService, UserDAOImpl userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String name = httpServletRequest.getParameter("firstName");
        long id = userDAO.getItemByUsername(name).getId();
        if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/index");
        } else if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/index");
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}