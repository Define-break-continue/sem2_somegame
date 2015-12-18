package ru.bagrusss.apiservlets;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Admin extends BaseServlet {

    public static final String URL = "/admin";
    public static final String SHUTDOWN_MESSAGE = "Server will be down after: ";

    @Override
    public void doGet(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String timeString = req.getParameter("shutdown");
        pageVariables.put("regUsers", mAccountService.getCountRegisteredUsers());
        pageVariables.put("actUsers", mAccountService.getCountActivatedUsers());
        if (timeString != null) {
            try {
                stopServer(Integer.valueOf(timeString));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        pageVariables.put("status", "run");
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
