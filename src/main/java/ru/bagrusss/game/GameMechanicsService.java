package ru.bagrusss.game;

import ru.bagrusss.game.mechanics.field.EventsListener;
import ru.bagrusss.game.mechanics.field.GameField;
import ru.bagrusss.helpers.BaseInterface;
import ru.bagrusss.servces.database.dataset.UserDataSet;

/**
 * Created by vladislav
 */

@SuppressWarnings("unused")
public interface GameMechanicsService extends BaseInterface, EventsListener {

    GameField getGameField(byte roomId);

    boolean joinToGame(UserDataSet user);

    boolean leaveGame(long userId);

    byte getPlacesCount();

    byte getStatus();

}
