package ru.bagrusss.administration;

import org.jetbrains.annotations.NotNull;
import ru.bagrusss.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminPageServlet extends HttpServlet {

    public static final String ADMIN_PAGE_URL = "/admin";

    @Override
    public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String timeString = request.getParameter("shutdown");
        if (timeString != null) {
            try {
                finilazeServer(Integer.valueOf(timeString));
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        pageVariables.put("status", "run");
        response.getWriter().println(PageGenerator.getPage("admin.tml", pageVariables));
    }
    private void finilazeServer(int time){
        System.out.print((new StringBuilder("Server will be down after: ")).append(time).append(" ms").toString());
        new Thread(() -> {
            try{
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\nShutdown");
            System.exit(0);
        }).start();
    }
}
