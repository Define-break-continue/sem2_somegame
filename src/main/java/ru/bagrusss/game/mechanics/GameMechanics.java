package ru.bagrusss.game.mechanics;

import ru.bagrusss.game.field.GameField;
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
    public void onPointEated(byte gamerId) {

    }

    @Override
    public void onPackmanEated(byte gamerId) {

    }

    @Override
    public void onBonusGenerated(byte bonusId, GameField.Point point) {

    }

    @Override
    public void onPointGenerated(GameField.Point coordinate) {

    }

    @Override
    public void onPackmanGenerated(byte gamerId, GameField.Point coordinate) {

    }

}
