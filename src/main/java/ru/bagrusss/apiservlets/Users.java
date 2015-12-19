package ru.bagrusss.apiservlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Users extends BaseServlet {

    public static final String URL = "/user/*";
    public static final byte METHOD_UPDATE = 20;
    public static final byte METHOD_CHANGE_PASS = 21;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
