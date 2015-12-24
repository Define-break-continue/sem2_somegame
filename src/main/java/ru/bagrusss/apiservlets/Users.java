package ru.bagrusss.apiservlets;

import ru.bagrusss.helpers.Errors;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Users extends BaseServlet {

    public static final String URL = "/user/*";
    public static final byte METHOD_UPDATE = 20;
    public static final byte METHOD_CHANGE_PASS = 21;
    public static final byte METHOD_LOGOUT = 22;

    private final String urlPattern = "/([2][012])(/)?";
    private final Pattern pattern = Pattern.compile(urlPattern);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        byte method = getMethod(path, pattern);
        switch (method) {
            case METHOD_UPDATE:

                break;
            case METHOD_CHANGE_PASS:

                break;
            case METHOD_LOGOUT:
                Cookie[] cookies = req.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(ACCESS_TOKEN)) {
                        if (mAccountService.removeSession(cookie.getValue())) {
                            Errors.correct(resp, "OK");
                            return;
                        }
                        Errors.errorAPI(resp, Errors.CODE_USER_NOT_EXISTS, Errors.MESSAGE_USER_NOT_EXISTS);
                    }
                }
                break;
            default:
                Errors.error404(resp, "Not found");
        }
    }
}
