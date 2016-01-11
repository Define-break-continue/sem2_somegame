package ru.bagrusss.apiservlets.websocket;

import ru.bagrusss.helpers.BaseInterface;

/**
 * Created by vladislav
 */

public interface WebSocketService extends BaseInterface {

    void notifyMovement(int gamerId, String move);

    void notifyStartGame(long gameTimeMS);

    void notifyGameOver(int winnerId);

    void addUser(String email, GameWebSocket user);

    void removeUser(String email);

}
