package ru.bagrusss.game.mechanics.field;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class GameField {

    private static final byte DIRECTION_UP = 3;
    private static final byte DIRECTION_RIGHT = 0;
    private static final byte DIRECTION_DOWN = 1;
    private static final byte DIRECTION_LEFT = 2;

    private byte length;
    private byte height;

    private byte lastX;
    private byte lastY;

    private static final int STATE_WALL = 1000;
    private static final int STATE_EMPTY = 0;

    @SuppressWarnings("InstanceVariableNamingConvention")
    public static class Point implements Cloneable {
        int x;
        int y;
    }

    private int[][] field;

    private ConcurrentHashMap<Byte, List<Point>> mGamerUnits;

    private EventsListener listener;

    public GameField(byte length, byte height) {
        this.length = length;
        this.height = height;
        field = new int[height][length];
        lastX = (byte) (length - 1);
        lastY = (byte) (height - 1);
        mGamerUnits = new ConcurrentHashMap<>();
    }

    public GameField(byte length, byte height, EventsListener listener) {
        this(length, height);
        this.listener = listener;
    }

    public void moveUnits(byte gamerId, byte direction) {
        List<Point> units = mGamerUnits.get(gamerId);
        Point newPoint = new Point();
        StringBuilder movement = new StringBuilder();
        for (Point oldPoint : units) {
            switch (direction) {
                case DIRECTION_UP:
                    newPoint.x = oldPoint.x;
                    newPoint.y = oldPoint.y - 1;
                    break;
                case DIRECTION_RIGHT:
                    newPoint.x = oldPoint.x + 1;
                    newPoint.y = oldPoint.y;
                    break;
                case DIRECTION_LEFT:
                    newPoint.x = oldPoint.x - 1;
                    newPoint.y = oldPoint.y;
                    break;
                case DIRECTION_DOWN:
                    newPoint.x = oldPoint.x;
                    newPoint.y = oldPoint.y + 1;
                    break;
                default:
                    return;
            }
            int pointState = getFieldValue(newPoint);
            switch (pointState) {
                case STATE_EMPTY:
                    updateField(newPoint, getFieldValue(oldPoint));
                    updatePoint(oldPoint, newPoint);
                    movement.append(direction).append(';');
                    break;
                case STATE_WALL:
                    movement.append(';');
                    break;
                default:
                    if (pointState > 0) {
                        updateField(newPoint, getFieldValue(oldPoint));
                        updatePoint(oldPoint, newPoint);

                    }
            }
        }
        listener.onPackmansMoved(gamerId, movement.toString());
    }

    private void updateField(int x, int y, int val) {
        field[y > lastY ? 0 : y][x > lastX ? 0 : x] = val;
    }

    private void updateField(Point p, int val) {
        updateField(p.x, p.y, val);
    }

    private int getFieldValue(int x, int y) {
        return field[y][x];
    }

    private int getFieldValue(Point p) {
        return getFieldValue(p.x, p.y);
    }

    private void updatePoint(Point oldP, Point newP) {
        oldP.x = newP.x;
        oldP.y = newP.y;
    }

}
