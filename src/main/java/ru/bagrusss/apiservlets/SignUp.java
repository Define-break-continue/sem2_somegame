package ru.bagrusss.apiservlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@SuppressWarnings({"TooBroadScope", "UnusedAssignment"})
public class SignUp extends BaseServlet {

    public static final String URL = "/signup/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        UserDataSet user;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(ACCESS_TOKEN)) {
                user = mAccountService.getSession(cookie.getValue());
                Errors.errorAPI(resp, Errors.CODE_USER_AUTHORIZED, Errors.MESSAGE_USER_AUTHORIZED);
                return;
            }
        }
        JsonObject json;
        try {
            json = mGson.fromJson(req.getReader(), JsonObject.class);
        } catch (JsonSyntaxException e) {
            log.log(Level.SEVERE, e.getClass().getName(), e);
            Errors.errorInvalidJson(resp);
            return;
        }
        String email;
        String pass1;
        String pass2;
        String type;
        try {
            email = json.get(EMAIL).getAsString();
            pass1 = json.remove(PASSWORD1).getAsString();
            pass2 = json.remove(PASSWORD2).getAsString();
            type = json.get(TYPE).getAsString();
        } catch (NullPointerException e) {
            log.log(Level.SEVERE, e.getClass().getName(), e);
            Errors.errorAPI(resp, Errors.CODE_INVALID_REQUEST, Errors.MESSAGE_INVALID_REQUEST);
            return;
        }
        if (!pass1.equals(pass2)) {
            Errors.errorPasswordsNotMatch(resp);
            return;
        }
        user = new UserDataSet(email, pass1);
        long id = mAccountService.registerUser(email, user);
        user.setId(id);
        if (id != 0) {
            log.log(Level.INFO, "Registered user: id " + id + " email " + user.getEmail());
            mAccountService.addSession(generateKey(), user);
            String key = generateKey();
            Cookie cookie = new Cookie(ACCESS_TOKEN, key);
            cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(1));
            mAccountService.addSession(key, user);
            resp.addCookie(cookie);
            json.addProperty(ID, id);
            Errors.correct(resp, json);
        } else Errors.errorAPI(resp, Errors.CODE_USER_ALREADY_EXISTS, Errors.MESSAGE_USER_ALREADY_EXISTS);
    }
}
