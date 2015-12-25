package ru.bagrusss.main;

import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.apiservlets.*;
import ru.bagrusss.game.mechanics.GameMechanics;
import ru.bagrusss.game.mechanics.GameMechanicsService;
import ru.bagrusss.game.mechanics.ResultsGame;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.servces.account.AccountService;
import ru.bagrusss.servces.account.ServiceDB;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final String MESSAGE_RUN = "Run server at port ";

    public static final String SERVER_CONFIGS = "./resources/.cfg/server.json";
    public static final String RESOURCES_PATH = Paths.get("").toAbsolutePath() + "/resources";

    public static final byte CONFIGS_ERROR = 3;
    public static final byte INIT_ERROR = 5;
    public static final byte JETTY_ERROR = 10;

    private static final Context APP_CONTEXT = new Context();
    private static final Logger LOG = Logger.getLogger(Main.class.getCanonicalName());
    private static final Configs CONFIGS = new Configs();

    public static final String FRONTEND = "frontend";
    public static final String PORT = "port";


    private static class Configs {
        public JsonObject getConfs() {
            return confs;
        }

        JsonObject confs;

        public void setConfigs(JsonObject confs) {
            this.confs = confs;
        }

    }

    public static Context getAppContext() {
        return APP_CONTEXT;
    }

    private static void initContext() {
        try {
            ServiceDB db = new ServiceDB(Main.RESOURCES_PATH + "/.cfg/db.json");
            APP_CONTEXT.add(AccountService.class, db);
            APP_CONTEXT.add(ResultsGame.class, db);
            APP_CONTEXT.add(GameMechanicsService.class, new GameMechanics());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, Main.class.getName(), e);
            System.exit(INIT_ERROR);
        }
    }

    @Nullable
    public static JsonObject getFieldConfigs(String path) {
        try {
            return Resourses.readResourses(path, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            CONFIGS.setConfigs(Resourses.readResourses(SERVER_CONFIGS, JsonObject.class));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, Main.class.getName(), e);
            System.exit(CONFIGS_ERROR);
        }
        initContext();
        int port = CONFIGS.getConfs().get(PORT).getAsInt();
        LOG.log(Level.INFO, (new StringBuilder(MESSAGE_RUN)).append(port).append('\n').toString());
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Admin()), Admin.URL);
        context.addServlet(new ServletHolder(new SignIn()), SignIn.URL);
        context.addServlet(new ServletHolder(new SignUp()), SignUp.URL);
        context.addServlet(new ServletHolder(new Users()), Users.URL);
        context.addServlet(new ServletHolder(new Info()), Info.URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        String path = CONFIGS.getConfs().get(FRONTEND).getAsString();
        resourceHandler.setResourceBase(path);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        server.setHandler(handlers);
        try {
            try {
                server.start();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, Main.class.getName(), e);
                System.exit(JETTY_ERROR);
            }
            server.join();
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, Main.class.getName(), e);
            System.exit(JETTY_ERROR);
        }
    }
}
