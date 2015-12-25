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

    public static GameField.Point genetatePoint(int startX, int lastX, int startY, int lastY) {
        GameField.Point point = new GameField.Point();
        point.x = generateIntOnInterval(startX, lastX);
        point.y = generateIntOnInterval(startY, lastY);
        return point;
    }

}
