package ru.bagrusss.apiservlets.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * Created by vladislav
 */

@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/game/*"})
public class GameServlet extends WebSocketServlet {

    public static final String URL = "/game/*";
    private static final int LOGOUT_TIME = 150 * 1000;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new GameWebSocketCreator());
    }
}
