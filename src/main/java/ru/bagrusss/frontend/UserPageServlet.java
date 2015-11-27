package ru.bagrusss.frontend;

import org.jetbrains.annotations.NotNull;
import ru.bagrusss.servces.account.UserProfile;
import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервлет страницы инфы о пользователе
 */

public class UserPageServlet extends HttpServlet {

    public static final String URL = "/user";

    @Override
    protected void doGet(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String sessionId = req.getSession().getId();
        UserProfile user = AccountServiceFake.getInstance().getSession(sessionId);
        String name;
        String email;
        if (user != null) {
            name = user.getmUserLogin();
            email = user.getmUserEmail();
        } else {
            name = "Guest";
            email = "No info!";
        }
        pageVariables.put("name", name);
        pageVariables.put("email", email);
        resp.getWriter().println(PageGenerator.getPage("userpage.html", pageVariables));
    }

    @Override
    protected void doPost(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
