package ru.bagrusss.game.mechanics.room;

import ru.bagrusss.game.mechanics.field.EventsListener;
import ru.bagrusss.game.mechanics.field.GameField;

import java.util.List;

/**
 * Created by vladislav
 */

public class GameRoom implements EventsListener {

    private GameField gameField;
    private boolean status;


    @Override
    public void onPackmanEated(byte gamerId) {

    }

    @Override
    public void onPackmansMoved(byte gamerId, List<Integer> coordinates) {

    }

    @Override
    public void onPointsEated(byte gamerId, byte count) {

    }

    @Override
    public void onBonusActivated(byte gamerId, byte bonusId) {

    }
}
