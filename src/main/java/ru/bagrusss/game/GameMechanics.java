package ru.bagrusss.game;

import ru.bagrusss.game.mechanics.field.GameField;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladislav
 */

public class GameMechanics implements GameMechanicsService {

    private ConcurrentHashMap<Long, UserDataSet> mUsers;

    @Override
    public GameField getGameField(byte roomId) {
        return null;
    }

    @Override
    public boolean joinToGame(UserDataSet user) {
        return false;
    }

    @Override
    public boolean leaveGame(long userId) {
        return false;
    }

    @Override
    public byte getPlacesCount() {
        return 0;
    }

    @Override
    public byte getStatus() {
        return 0;
    }

    @Override
    public void onPackmansMoved(byte gamerId, String coordinates) {

    }

    @Override
    public void onBonusGenerated(byte bonusId) {

    }

    @Override
    public void onPointGenerated() {

    }

}
