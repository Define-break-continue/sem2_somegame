package ru.bagrusss.apiservlets.websocket;

import com.google.gson.JsonObject;
import ru.bagrusss.helpers.Errors;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladislav
 */

public class ServiceWS implements WebSocketService {


    private final ConcurrentHashMap<String, GameWebSocket> mUsers = new ConcurrentHashMap<>();

    @Override
    public void notifyMovement(int gamerId, String move) {
        JsonObject resp = new JsonObject();
        resp.addProperty("gamerId", gamerId);
        resp.addProperty("move", move);
        for (GameWebSocket game : mUsers.values()) {
            game.move(resp.toString());
        }
    }

    @Override
    public void notifyStartGame() {

    }

    @Override
    public void notifyGetField() {

    }

    @Override
    public void notifyFinishGame(int wId) {

    }

    @Override
    public void addUser(String session, GameWebSocket user) {
        mUsers.put(session, user);
    }

    @Override
    public void removeUser(String session) {
        mUsers.remove(session);
    }

}
