package ru.bagrusss.apiservlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vladislav
 */

public class Info extends BaseServlet {
    public static final String URL = "/info/*";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}
