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

    /**
     * Генерирует игровой id пользователя
     *
     * @param user UserDataSet
     * @return true если игра не начата и пользователь не присоединился
     */
    boolean joinToGame(UserDataSet user, int gamerId);

    void leaveGame(int gamerId);

    void moveGamerUnits(int gamerId, byte dir);

    byte getStatus();

    int generateGameId();

    boolean hasPlaces();

}
