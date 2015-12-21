package ru.bagrusss.apiservlets;

import ru.bagrusss.helpers.Errors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Users extends BaseServlet {

    public static final String URL = "/user/*";
    public static final byte METHOD_UPDATE = 20;
    public static final byte METHOD_CHANGE_PASS = 21;

    private final String urlPattern = "/([2][01])(/)?";
    private final Pattern pattern = Pattern.compile(urlPattern);

    byte getMethod(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? Byte.valueOf(matcher.group(1)) : 0;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        byte method = getMethod(path);
        switch (method) {
            case METHOD_UPDATE:

                break;
            case METHOD_CHANGE_PASS:

                break;
            default:
                Errors.errorAPI(resp, Errors.CODE_INVALID_REQUEST, "Метод не существует!");
        }
    }
}
