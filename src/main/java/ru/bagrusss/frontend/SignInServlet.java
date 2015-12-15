package ru.bagrusss.frontend;


import org.jetbrains.annotations.NotNull;
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

public class SignInServlet extends HttpServlet {

    private final AccountServiceFake accountServiceFake;
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_LOGOUT = "logout";
    public static final String URL = "/signin";


    public SignInServlet(AccountServiceFake accountServiceFake) {
        this.accountServiceFake = accountServiceFake;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        if (req.getParameter(PARAM_LOGOUT) != null) {
            doLogout(req);
            JSONObject respText = new JSONObject();
            try {
                respText.put("status", HttpServletResponse.SC_OK);
                respText.put("nextUrl", "/");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resp.getWriter().println(respText.toString());
            return;
        }
        resp.getWriter().println(PageGenerator.getPage("signin.html", null));
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter(PARAM_NAME);
        String password = req.getParameter(PARAM_PASSWORD);
        UserProfile user = accountServiceFake.getUser(name);
        resp.setStatus(HttpServletResponse.SC_OK);
        JSONObject responseText = new JSONObject();
        String res;
        if (user != null && user.getmUserPassword().equals(password)) {
            res = "OK";
            accountServiceFake.doSaveUser(req, user);
        } else res = "FAIL";
        try {
            responseText.put("status", HttpServletResponse.SC_OK);
            responseText.put("result", res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resp.getWriter().println(responseText.toString());
    }

    void doLogout(@NotNull HttpServletRequest request) {
        accountServiceFake.removeSession(request.getSession().getId());
    }

}
