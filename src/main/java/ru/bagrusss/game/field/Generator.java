package ru.bagrusss.game.field;

import java.util.Random;

/**
 * Created by vladislav
 */
public class Generator {
    private static final Random RANDOM = new Random();

    public static int generateIntOnInterval(int start, int last) {
        return RANDOM.nextInt(last - start + 1) + start;
    }

    public static GameField.Point genetatePoint(int lastX, int lastY) {
        GameField.Point point = new GameField.Point(0, 0);
        point.x = generateIntOnInterval(0, lastX);
        point.y = generateIntOnInterval(0, lastY);
        return point;
    }

}
