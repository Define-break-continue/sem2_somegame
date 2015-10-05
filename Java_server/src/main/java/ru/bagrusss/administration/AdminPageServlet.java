package ru.bagrusss.administration;

import org.jetbrains.annotations.NotNull;
import ru.bagrusss.servces.AccountService;
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
    public static final String SHUTDOWN_MESSAGE="Server will be down after: ";

    @Override
    public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String timeString = request.getParameter("shutdown");
        pageVariables.put("regUsers", AccountService.getInstance().getCountRegisteredUsers());
        pageVariables.put("actUsers", AccountService.getInstance().getCountActivatedUsers());
        if (timeString != null) {
            try {
                stopServer(Integer.valueOf(timeString));
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        pageVariables.put("status", "run");
        response.getWriter().println(PageGenerator.getPage("admin.tml", pageVariables));
    }
    private void stopServer(int time){
        System.out.print((new StringBuilder(SHUTDOWN_MESSAGE)).append(time).append(" ms").toString());
        new Thread(() -> {
            try{
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
