package ru.bagrusss.main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.bagrusss.apiservlets.*;
import ru.bagrusss.helpers.Context;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.helpers.ServerConfigs;
import ru.bagrusss.servces.account.AccountService;
import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.servces.database.DataBaseService;
import ru.bagrusss.servces.database.ServiceDB;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    private static final String MESSAGE_RUN = "Run server at port ";

    public static final String SERVER_CONFIGS = ".//resources//.cfg//server.json";
    public static final String RESOURCE_PATH = Paths.get("").toAbsolutePath() + "//resources";

    public static final byte DB_ERROR = 2;
    public static final byte DB_CONFIGS_ERROR = 4;
    public static final byte CONFIGS_ERROR = 3;

    private static Context appContext = new Context();

    public static Context getAppContext() {
        return appContext;
    }

    private static void initContext() {
        try {
            appContext.add(DataBaseService.class, new ServiceDB());
            appContext.add(AccountService.class, new AccountServiceFake());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initContext();
        ServerConfigs conf = null;
        try {
            conf = Resourses.readResourses(SERVER_CONFIGS, ServerConfigs.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(CONFIGS_ERROR);
        }
        int port = conf.getPort();
        System.out.println((new StringBuilder(MESSAGE_RUN)).append(port).append('\n'));
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Admin()), Admin.URL);
        context.addServlet(new ServletHolder(new SignIn()), SignIn.URL);
        context.addServlet(new ServletHolder(new SignUp()), SignUp.URL);
        context.addServlet(new ServletHolder(new User()), User.URL);
        context.addServlet(new ServletHolder(new Info()), Info.URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        String path = conf.getFrontendPath();
        resourceHandler.setResourceBase(path);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        server.setHandler(handlers);
        try {
            try {
                server.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
