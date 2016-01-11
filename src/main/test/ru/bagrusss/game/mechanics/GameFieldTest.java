package ru.bagrusss.game.mechanics;

import org.junit.Test;
import ru.bagrusss.game.field.EventsListener;
import ru.bagrusss.game.field.GameField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladislav
 */

public class GameFieldTest {

    public static final int MAX_POINTS = 20;
    public static final int MAX_WALLS = 11;
    private GameField gameField = new GameField(10, 10, new MyEventsListener());


    @Test
    public void testPrepareFieldMoveUnits() throws Exception {
        List<Integer> ids = new ArrayList<>(2);
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);
        ids.add(5);
        gameField.setMaxPoints(MAX_POINTS);
        gameField.setMaxWalls(MAX_WALLS);
        gameField.prepareFieldToGame(ids);
        byte id = 1;
        gameField.moveUnits(id, GameField.DIRECTION_UP);
    }

    private static class MyEventsListener implements EventsListener {


        @Override
        public void onPackmansMoved(int gamerId, String coordinates) {

        }

        @Override
        public void onPointEated(int gamerId) {

        }

        @Override
        public void onPackmanEated(int whoId) {

        }

    }
}