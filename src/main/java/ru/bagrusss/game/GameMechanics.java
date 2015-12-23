package ru.bagrusss.game;

import ru.bagrusss.game.mechanics.field.GameField;
import ru.bagrusss.game.mechanics.room.GameRoom;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladislav
 */

public class GameMechanics implements GameMechanicsService {

    ConcurrentHashMap<Byte, GameRoom> rooms = new ConcurrentHashMap<>();

    @Override
    public GameField getGameField(byte roomId) {
        return null;
    }

    @Override
    public boolean moveGamerUnits(byte roomId, byte gamerId, byte direction) {
        return false;
    }

    @Override
    public long createRoom() {
        return 0;
    }

    @Override
    public GameRoom getRoom(byte id) {
        return null;
    }

    @Override
    public int getCountRooms() {
        return 0;
    }
}
