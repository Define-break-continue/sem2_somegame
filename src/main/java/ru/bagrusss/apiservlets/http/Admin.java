package ru.bagrusss.apiservlets.http;

import org.jetbrains.annotations.NotNull;
import ru.bagrusss.main.Context;

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
    public static final String STATUS_RUN = "RUN";
    public static final String STATUS_STOPPING = "STOPING";
    public static final String STATUS_STOP_ERROR = "Error";
    public static final String SHUTDOWN = "shutdown";

    public Admin(Context context) {
        super(context);
    }

    @Override
    public void doGet(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        String timeString = req.getParameter(SHUTDOWN);
        if (timeString != null) {
            try {
                stopServer(Integer.valueOf(timeString));
            } catch (NumberFormatException e) {
                System.out.println(STATUS_STOP_ERROR);
            }
            return;
        }
        System.out.println(STATUS_RUN);
    }

    private void stopServer(int time) {
        System.out.println(SHUTDOWN_MESSAGE + time + "ms");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\nShutdown ");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(STATUS_STOPPING + dateFormat.format(date));
        System.exit(0);
    }
}
