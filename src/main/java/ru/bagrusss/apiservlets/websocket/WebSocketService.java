package ru.bagrusss.apiservlets.websocket;

import ru.bagrusss.helpers.BaseInterface;

/**
 * Created by vladislav
 */

@SuppressWarnings("unused")
public interface WebSocketService extends BaseInterface {

    void notifyMovement(int gamerId, String move);

    void notifyStartGame();

    void notifyGetField();

    void notifyFinishGame(int winnerId);

    void addUser(String email, GameWebSocket user);

    void removeUser(String email);

}
