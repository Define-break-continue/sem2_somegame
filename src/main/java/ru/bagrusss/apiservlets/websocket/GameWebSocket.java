package ru.bagrusss.apiservlets.websocket;

import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.bagrusss.apiservlets.http.BaseServlet;
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

    public static final String PARAMS = "params";

    private static final String METHOD_ID = "methodId";
    private static final String DIRECTION = "direction";
    private static final String GAMER_ID = "gamerId";
    private static final String METHOD_MOVE = "move";
    private static final String GAME_TIME = "gameTime";
    private static final String WINNER = "winner";

    private static final byte GET_FIELD = 30;
    private static final byte MOVE = 31;
    private static final byte JOIN = 32;
    private static final byte LEAVE = 33;
    private static final byte GAME_STARTED = 34;
    private static final byte GAME_OVER = 35;
    private static final byte GENERATE_GAME_ID = 36;
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

    void moved(int gamerId, String moveStr) {
        try {
            JsonObject resp = new JsonObject();
            resp.addProperty("gamerId", gamerId);
            resp.addProperty(METHOD_MOVE, moveStr);
            mSession.getRemote().sendString(resp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void gameStared(long gameTime) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(METHOD_ID, GAME_STARTED);
        jsonObject.addProperty(GAME_TIME, gameTime);
        try {
            mSession.getRemote().sendString(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void gameOver(int winner) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(METHOD_ID, GAME_OVER);
        jsonObject.addProperty(WINNER, winner);
        try {
            mSession.getRemote().sendString(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void userJoined(String email) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(METHOD_ID, JOIN);
        jsonObject.addProperty(BaseServlet.EMAIL, email);
        try {
            mSession.getRemote().sendString(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void userLeave(String email) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(METHOD_ID, LEAVE);
        jsonObject.addProperty(BaseServlet.EMAIL, email);
        try {
            mSession.getRemote().sendString(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(Reader reader) {
        JsonObject jsonMms = Resources.GSON.fromJson(reader, JsonObject.class);
        LOG.log(Level.INFO, jsonMms.toString());
        byte methodId = jsonMms.get(METHOD_ID).getAsByte();
        switch (methodId) {
            case GET_FIELD:
                JsonObject field = mServiceGM.getGameFieldState();
                field.addProperty(METHOD_ID, GET_FIELD);
                try {
                    mSession.getRemote().sendString(field.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case MOVE:
                JsonObject params = jsonMms.get(PARAMS).getAsJsonObject();
                int id = params.get(GAMER_ID).getAsInt();
                byte dir = params.get(DIRECTION).getAsByte();
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(METHOD_ID, GENERATE_GAME_ID);
        jsonObject.addProperty(GAMER_ID, mGamerId);
        try {
            mSession.getRemote().sendString(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mServiceWS.addUser(mUser.getEmail(), this);
        mServiceGM.joinToGame(mUser, mGamerId);
        LOG.log(Level.INFO, "User " + mUser.getEmail() + " connected to game");
    }

    @OnWebSocketClose
    public void onClose(Session session, int code, String msg) {
        mServiceWS.removeUser(mUser.getEmail());
        mServiceGM.leaveGame(mGamerId);
        LOG.log(Level.INFO, "User " + mUser.getEmail() + " disconnected");
    }

}
