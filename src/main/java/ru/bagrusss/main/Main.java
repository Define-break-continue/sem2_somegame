package ru.bagrusss.main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.administration.AdminPageServlet;
import ru.bagrusss.frontend.SignInServlet;
import ru.bagrusss.frontend.SignUpServlet;
import ru.bagrusss.frontend.UserPageServlet;
import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.servces.database.DataBaseService;
import ru.bagrusss.servces.database.DataBaseServiceImpl;

public class Main {

    private static final String START_MESSAGE = "Run server at port ";
    public static final String RESOURSE_DIR = "frontend/public_html";
    public static final String ERROR_MESSAGE = "Use port as the first argument";

    public static final int DB_ERROR = 2;

    static Context appContext = new Context();

    public static void main(@NotNull String[] args) {
        if (args.length != 1) {
            System.out.append(ERROR_MESSAGE);
            System.exit(1);
        }
        try {
            appContext.add(DataBaseService.class, DataBaseServiceImpl.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!((DataBaseServiceImpl) appContext.get(DataBaseService.class)).checkDataBase())
            System.exit(DB_ERROR);

        String portString = args[0];
        int port = Integer.valueOf(portString);
        System.out.println((new StringBuilder(START_MESSAGE)).append(portString).append('\n'));

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new AdminPageServlet()), AdminPageServlet.URL);

        context.addServlet(new ServletHolder(new SignInServlet(AccountServiceFake.getInstance())), SignInServlet.URL);
        context.addServlet(new ServletHolder(new SignUpServlet(AccountServiceFake.getInstance())), SignUpServlet.URL);
        context.addServlet(new ServletHolder(new UserPageServlet()), UserPageServlet.URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(RESOURSE_DIR);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        server.setHandler(handlers);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
