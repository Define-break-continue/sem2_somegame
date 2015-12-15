package ru.bagrusss.main;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;

/**
 * Created by vladislav
 */

public class BaseServlet extends HttpServlet {
    protected final Gson mGson = new Gson();
    public static final String DEFAULT_ENCODING = "UTF-8";

    public BaseServlet(){

    }
}
