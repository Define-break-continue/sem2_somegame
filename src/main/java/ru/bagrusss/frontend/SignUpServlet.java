package ru.bagrusss.frontend;

import org.json.JSONException;
import org.json.JSONObject;
import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.servces.account.UserProfile;
import ru.bagrusss.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountServiceFake accountServiceFake;
    public static final String URL = "/signup";

    public SignUpServlet(AccountServiceFake accountServiceFake) {
        this.accountServiceFake = accountServiceFake;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println(PageGenerator.getPage("signup.html", null));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String result;
        String message;
        JSONObject response = new JSONObject();
        if (email == null || password == null) {
            result = "FAIL";
            message = "Encorrect parameters";
        } else {
            UserProfile user = new UserProfile(name, password, email);
            if (accountServiceFake.addUser(email, user)) {
                result = "OK";
                message = "User successfuly created!";
                accountServiceFake.doSaveUser(req, user);
            } else {
                result = "FAIL";
                message = "User already exist! Try agan.";
            }
        }
        try {
            response.put("status", HttpServletResponse.SC_OK);
            response.put("resources", result);
            response.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(response.toString());
    }
}
