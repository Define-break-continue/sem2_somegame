package ru.bagrusss.game.mechanics;

import com.google.gson.JsonObject;
import ru.bagrusss.game.field.EventsListener;
import ru.bagrusss.helpers.BaseInterface;
import ru.bagrusss.servces.database.dataset.UserDataSet;

/**
 * Created by vladislav
 */


@SuppressWarnings("unused")
public interface GameMechanicsService extends BaseInterface, EventsListener {

    int PACKMAN_SCORE = 10;
    int POINT_SCORE = 1;
    int STATUS_PLAY = 1;
    int STATUS_WAIT_START = 2;
    int STATUS_WAIT = 0;
    int TIME_WAIT_GAME = 10000;
    int TIME_GAME = 150000;
    int MIN_USERS_FOR_GAME = 2;

    /**
     * Генерирует игровой id пользователя
     *
     * @param user UserDataSet
     *
     */

    void joinToGame(UserDataSet user, int gamerId);

    void leaveGame(int gamerId);

    void moveGamerUnits(int gamerId, byte dir);

    byte getStatus();

    int generateGameId();

    boolean hasPlaces();

    JsonObject getGameFieldState();

}
