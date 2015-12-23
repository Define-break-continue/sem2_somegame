package ru.bagrusss.game;

import ru.bagrusss.game.mechanics.field.GameField;
import ru.bagrusss.game.mechanics.room.GameRoom;
import ru.bagrusss.helpers.BaseInterface;

/**
 * Created by vladislav on 23.12.15.
 */
@SuppressWarnings("unused")
public interface GameMechanicsService extends BaseInterface {

    GameField getGameField(byte roomId);

    boolean moveGamerUnits(byte roomId, byte gamerId, byte direction);

    long createRoom();

    GameRoom getRoom(byte id);

    int getCountRooms();

}
