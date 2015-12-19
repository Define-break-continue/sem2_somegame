package ru.bagrusss.apiservlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends BaseServlet {

    public static final String URL = "/signup/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject json;
        try {
            json = mGson.fromJson(req.getReader(), JsonObject.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
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
            Errors.errorAPI(resp, Errors.CODE_INVALID_REQUEST, Errors.MESSAGE_INVALID_REQUEST);
            return;
        }
        if (!pass1.equals(pass2)) {
            Errors.errorPasswordsNotMatch(resp);
            return;
        }
        UserDataSet user = new UserDataSet(email, pass1);
        long id = mAccountService.registerUser(email, user);
        user.setId(id);
        if (id != 0) {
            json.addProperty(ID, id);
            Errors.correct(resp, json);
        } else Errors.errorAPI(resp, Errors.CODE_USER_ALREADY_EXISTS, Errors.MESSAGE_USER_ALREADY_EXISTS);
    }
}
