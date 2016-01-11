package ru.bagrusss.apiservlets.http;


import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

public class SignIn extends BaseServlet {

    public static final String URL = "/signin/";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie cookie = getCookieByName(cookies, ACCESS_TOKEN);
        if (cookie != null && mAccountService.getSession(cookie.getValue()) != null) {
            Errors.errorAPI(resp, Errors.CODE_USER_AUTHORIZED, Errors.MESSAGE_USER_AUTHORIZED);
            return;
        }
        JsonObject params;
        try {
            params = mGson.fromJson(req.getReader(), JsonObject.class);
        } catch (JsonSyntaxException e) {
            Errors.errorInvalidJson(resp);
            return;
        }
        String email;
        String password;
        try {
            email = params.get(EMAIL).getAsString();
            password = params.remove(PASSWORD).getAsString();
        } catch (NullPointerException e) {
            Errors.errorAPI(resp, Errors.CODE_INVALID_REQUEST, Errors.MESSAGE_INVALID_REQUEST);
            return;
        }
        UserDataSet user = mAccountService.getUser(email, password);
        if (user != null) {
            String key = generateKey();
            cookie = new Cookie(ACCESS_TOKEN, key);
            mAccountService.addSession(key, user);
            resp.addCookie(cookie);
            log.log(Level.INFO, "Authorize user: " + user.getEmail());
            params.addProperty(ID, user.getId());
            Errors.correct(resp, params);
            return;
        }
        Errors.errorAPI(resp, Errors.CODE_USER_NOT_EXISTS, Errors.MESSAGE_USER_NOT_EXISTS);
    }
}
