package ru.bagrusss.apiservlets.websocket;

import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.bagrusss.game.mechanics.GameMechanicsService;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.helpers.Resources;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vladislav
 */

@WebSocket
public class GameWebSocket {

    private static final String METHOD_ID = "methodId";
    private static final byte GET_FIELD = 30;
    private static final byte MOVE = 31;

    private static final Logger LOG = Logger.getLogger(GameWebSocket.class.getName());
    private final WebSocketService mServiceWS = (WebSocketService) Main.getAppContext().get(WebSocketService.class);
    private final GameMechanicsService mServiceGM = (GameMechanicsService) Main.getAppContext()
            .get(GameMechanicsService.class);
    private final UserDataSet mUser;
    private final int mGamerId;
    private Session mSession;

    public GameWebSocket(UserDataSet user, int gameId) {
        this.mUser = user;
        this.mGamerId = gameId;
    }

    void move(String res) {
        try {
            mSession.getRemote().sendString(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(Reader reader) {
        JsonObject jsonMms = Resources.GSON.fromJson(reader, JsonObject.class);
        byte methodId = jsonMms.get(METHOD_ID).getAsByte();
        LOG.log(Level.INFO, jsonMms.toString());
        switch (methodId) {
            case GET_FIELD:

                break;
            case MOVE:
                JsonObject params = jsonMms.get("params").getAsJsonObject();
                int id = params.get("gamerId").getAsInt();
                byte dir = params.get("direction").getAsByte();
                mServiceGM.moveGamerUnits(id, dir);
                break;
            default:
                try {
                    mSession.getRemote().sendString(Errors.MESSAGE_INVALID_REQUEST);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        mSession = session;
        mServiceWS.addUser(mUser.getEmail(), this);
        mServiceGM.joinToGame(mUser, mGamerId);
        LOG.log(Level.INFO, "User " + mUser.getEmail() + " connected to game");
    }

    @OnWebSocketClose
    public void onClose(Session session, int code, String msg) {
        mServiceWS.removeUser(mUser.getEmail());
        LOG.log(Level.INFO, "User " + mUser.getEmail() + " disconnected");
    }

}
