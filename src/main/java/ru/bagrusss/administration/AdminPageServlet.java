package ru.bagrusss.administration;

import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminPageServlet extends HttpServlet {

    public static final String URL = "/admin";
    public static final String SHUTDOWN_MESSAGE = "Server will be down after: ";
    public static final String STATUS_RUN="RUN";
    public static final String STATUS_STOPPING="STOPING";
    public static final String STATUS_STOP_ERROR="Error";

    @Override

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String timeString = req.getParameter("shutdown");
        pageVariables.put("regUsers", AccountServiceFake.getInstance().getCountRegisteredUsers());
        pageVariables.put("actUsers", AccountServiceFake.getInstance().getCountActivatedUsers());
        if (timeString != null) {
            try {
                long ms = Integer.valueOf(timeString);
                stopServer(ms);
                pageVariables.put("status", STATUS_STOPPING);
            } catch (NumberFormatException e) {
                pageVariables.put("status", STATUS_STOP_ERROR);
            }
        } else {
            pageVariables.put("status", STATUS_RUN);
        }
        resp.getWriter().println(PageGenerator.getPage("admin.tml", pageVariables));
    }

    public void stopServer(long time) {
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