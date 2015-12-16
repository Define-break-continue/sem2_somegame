package ru.bagrusss.frontend;

import ru.bagrusss.servces.account.AccountService;
import ru.bagrusss.servces.account.UserProfile;
import ru.bagrusss.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UserPageServlet extends HttpServlet {

    public static final String URL = "/user";
    private AccountService mAccounts;

    public UserPageServlet(AccountService accountServiceFake) {
        this.mAccounts = accountServiceFake;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String sessionId = req.getSession().getId();
        UserProfile user = mAccounts.getSession(sessionId);
        String name;
        String email;
        if (user != null) {
            name = user.getUserLogin();
            email = user.getUserEmail();
        } else {
            name = "Guest";
            email = "No info!";
        }
        pageVariables.put("name", name);
        pageVariables.put("email", email);
        resp.getWriter().println(PageGenerator.getPage("userpage.html", pageVariables));
    }


}
