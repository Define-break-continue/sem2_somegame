package ru.bagrusss.apiservlets;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Admin extends BaseServlet {

    public static final String URL = "/admin";
    public static final String SHUTDOWN_MESSAGE = "Server will be down after: ";

    @Override
    public void doGet(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(JSON_TYPE);
        resp.setStatus(HttpServletResponse.SC_OK);
        String timeString = req.getParameter("shutdown");
        if (timeString != null) {
            try {
                stopServer(Integer.valueOf(timeString));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopServer(int time) {
        System.out.print((new StringBuilder(SHUTDOWN_MESSAGE)).append(time).append(" ms").toString());
        new Thread(() -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\nShutdown ");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            System.exit(0);
        }).start();
    }

}
