package ru.bagrusss.game.mechanics;

import org.junit.Test;
import ru.bagrusss.game.field.EventsListener;
import ru.bagrusss.game.field.GameField;
import ru.bagrusss.main.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladislav
 */

public class GameFieldTest {

    private GameField gameField = new GameField(Main.RESOURCES_PATH + "/.data/field.json", new EventsListener() {
        @Override
        public void onPackmansMoved(byte gamerId, String coordinates) {
            System.out.println(gamerId + "  " + coordinates);
        }

        @Override
        public void onPointEated(byte gamerId) {
            System.out.println(gamerId + " eat point ");
        }

        @Override
        public void onPackmanEated(byte whoEatId) {
            System.out.println(whoEatId + " eat pcm ");
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
    });

    @Test
    public void testPrepareFieldToGame() throws Exception {
        List<Byte> ids = new ArrayList<>(2);
        ids.add((byte) 1);
        ids.add((byte) 2);
        gameField.prepareFieldToGame(ids);
        byte id = 1;
        gameField.moveUnits(id, GameField.DIRECTION_UP);
    }

    @Test
    public void testMoveUnits() throws Exception {

    }

}