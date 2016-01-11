package ru.bagrusss.apiservlets.websocket;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladislav
 */

public class ServiceWS implements WebSocketService {

    private final ConcurrentHashMap<String, GameWebSocket> mUsers = new ConcurrentHashMap<>();

    @Override
    public void notifyMovement(int gamerId, String move) {
        for (GameWebSocket game : mUsers.values()) {
            game.moved(gamerId, move);
        }
    }

    @Override
    public void notifyStartGame(long timeMS) {
        for (GameWebSocket game : mUsers.values()) {
            game.gameStared(timeMS);
        }
    }


    @Override
    public void notifyGameOver(int wId) {
        for (GameWebSocket game : mUsers.values()) {
            game.gameOver(wId);
        }
    }

    @Override
    public void addUser(String email, GameWebSocket socket) {
        for (GameWebSocket game : mUsers.values()) {
            game.userJoined(email);
        }
        mUsers.put(email, socket);
    }

    @Override
    public void removeUser(String email) {
        mUsers.remove(email);
        for (GameWebSocket game : mUsers.values()) {
            game.userLeave(email);
        }
    }

}
