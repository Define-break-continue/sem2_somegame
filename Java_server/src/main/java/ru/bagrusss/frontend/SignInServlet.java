package ru.bagrusss.frontend;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import ru.bagrusss.models.UserProfile;
import ru.bagrusss.servces.AccountService;
import ru.bagrusss.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private AccountService accountService;
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_NAME = "name";
    public static final String URL = "/signin";


    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("authform.tml", null));
    }

    @Override
    public void doPost(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        UserProfile user = accountService.getUser(email);
        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject responseText = new JSONObject();
        String res;
        if (user != null && user.getUserPassword().equals(password)) {
            res="OK";
        } else{
            res="FAIL";
        }
        try {
            responseText.put("status", HttpServletResponse.SC_OK);
            responseText.put("result", res);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response.getWriter().println(responseText.toString());
    }
}
