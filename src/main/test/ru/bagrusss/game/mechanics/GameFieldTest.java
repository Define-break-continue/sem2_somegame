package ru.bagrusss.game.mechanics;

import org.junit.Assert;
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
    static int pacs = 5;

    @Test
    public void testPrepareFieldMoveUnits() throws Exception {
        List<Integer> ids = new ArrayList<>(2);
        ids.add(1);
        GameField gameField = new GameField(10, 10, new MyEventsListener());
        gameField.setMaxPoints(MAX_POINTS);
        gameField.setMaxWalls(MAX_WALLS);
        gameField.setMaxPacmansForGamers(pacs);
        gameField.prepareFieldToGame(ids);
        gameField.moveUnits(1, GameField.DIRECTION_UP);
    }

    private static class MyEventsListener implements EventsListener {

        @Override
        public void onPackmansMoved(int gamerId, String coordinates) {
            System.out.println(coordinates);
            String[] movments = coordinates.split(";");
            Assert.assertEquals(movments.length, pacs);
        }

        @Override
        public void onPointEated(int gamerId) {

        }

        @Override
        public void onPackmanEated(int whoId) {

        }

    }
}