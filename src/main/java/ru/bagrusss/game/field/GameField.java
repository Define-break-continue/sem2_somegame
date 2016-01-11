package ru.bagrusss.game.field;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.helpers.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Поле представляет собой матрицу int
 * 0 - пусто,
 * > 0
 * Пакмен:
 * - младший разрят - id [1, 10] пакмена игрока
 * - 2й разряд  - id игрока [1,10]
 * - 3й пуст
 * - 4й 1й бит установлен
 * Стена - 2й бит 4го разряда - 1, В 3м разряде id Бонуса
 * Точка - 3й бит 4го разряда - 1, остальные разряды пусты
 */


@SuppressWarnings({"OverlyComplexMethod", "unused"})
public class GameField {

    public static final byte DIRECTION_UP = 3;
    public static final byte DIRECTION_RIGHT = 0;
    public static final byte DIRECTION_DOWN = 1;
    public static final byte DIRECTION_LEFT = 2;

    public static final int MAX_FIELD_SQUARE = 10000;

    private static final Logger LOG = Logger.getLogger(GameField.class.getName());
    private static final int FIRST_8_BIT = 0XFF;
    private static final int SECOND_8_BIT = FIRST_8_BIT << 8;
    private static final int TO_SECOND_8_BIT = 8;
    private static final int PACMAN_MASK = 0x0100FFFF;
    private static final int PACMAN_BIT = 0x01000000;
    private static final int WALL = 0x2000000;
    private static final int POINT = 0x3000000;
    private static final int BONUS = 0x4000000;
    private static final int EMPTY = 0;
    private int mLength;
    private int mHeight;
    private byte lastX;
    private byte lastY;
    private int mMaxPoints;

    public int getMaxPacmansForGamer() {
        return mMaxPacmansForGamer;
    }

    public void setMaxPacmansForGamer(int mMaxPacmansForGamer) {
        this.mMaxPacmansForGamer = mMaxPacmansForGamer;
    }

    private int mMaxPacmansForGamer;
    private int mMaxWalls;
    private byte mGamersCount;
    private int[][] field;
    private ConcurrentHashMap<Integer, List<Point>> mGamerIdUnits;
    private EventsListener mListener;

    public GameField(int length, int height, @NotNull EventsListener listener) {
        int square = length * height;
        assert square > 4 && square <= MAX_FIELD_SQUARE;
        field = new int[height & FIRST_8_BIT][length & FIRST_8_BIT];
        mHeight = height;
        mLength = length;
        lastX = (byte) (length - 1);
        lastY = (byte) (height - 1);
        mGamerIdUnits = new ConcurrentHashMap<>();
        this.mListener = listener;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int mLength) {
        this.mLength = mLength;
    }

    public int getMaxPoints() {
        return mMaxPoints;
    }

    public void setMaxPoints(int mMaxPoints) {
        this.mMaxPoints = mMaxPoints;
    }

    public int getMaxWalls() {
        return mMaxWalls;
    }

    public void setMaxWalls(int mMaxWalls) {
        this.mMaxWalls = mMaxWalls;
    }

    public JsonObject getFieldConfigs(String path) {
        try {
            return Resources.readResourses(path, JsonObject.class);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, this.getClass().getName(), e);
            return new JsonObject();
        }
    }

    public void prepareFieldToGame(@NotNull List<Integer> gamerIds) {
        generateObject(WALL, mMaxWalls);
        generateObject(POINT, mMaxPoints);
        generatePackmans(gamerIds);
    }

    private void generatePackmans(@NotNull List<Integer> gamerIds) {
        for (int gmId : gamerIds) {
            int remaindGeneratePcms = (mMaxPacmansForGamer - 1);
            List<Point> packmans = new ArrayList<>(mMaxPacmansForGamer);
            while (remaindGeneratePcms >= 0) {
                Point p = Generator.genetatePoint(0, lastX, 0, lastY);
                int val = getFieldValue(p);
                if (val == 0) {
                    updateFieldValue(p, (gmId << TO_SECOND_8_BIT) + remaindGeneratePcms + PACMAN_BIT);
                    packmans.add(p);
                    --remaindGeneratePcms;
                }
            }
            mGamerIdUnits.put(gmId, packmans);
        }
    }

    private void generateObject(int what, int count) {
        LOG.log(Level.INFO, "generate " + count + " предметов " + (what == WALL ? "стен" : "точек"));
        while (count > 0) {
            Point p = Generator.genetatePoint(0, lastX, 0, lastY);
            if (getFieldValue(p) == 0) {
                updateFieldValue(p, what);
                --count;
            }
        }
    }

    public void moveUnits(int gamerId, byte direction) {
        List<Point> units = mGamerIdUnits.get(gamerId);
        Point newPoint = new Point();
        StringBuilder movement = new StringBuilder();
        for (Point oldPoint : units) {
            //пакмен еще жив, попробуем его передвинуть
            if (oldPoint.x >= 0 && oldPoint.y >= 0) {
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
                if (newPoint.x > lastX)
                    newPoint.x = 0;
                else if (newPoint.x < 0)
                    newPoint.x = lastX;
                if (newPoint.y > lastY)
                    newPoint.y = 0;
                else if (newPoint.y < 0)
                    newPoint.y = lastY;
                int newPointState = getFieldValue(newPoint);
                switch (newPointState) {
                    case EMPTY:
                        updateFieldValue(newPoint, getFieldValue(oldPoint));
                        updatePoint(oldPoint, newPoint);
                        movement.append(direction);
                        break;
                    case WALL:
                        System.out.println("wall");
                        break;
                    case BONUS:

                        break;
                    case POINT:
                        updateFieldValue(newPoint, getFieldValue(oldPoint));
                        updatePoint(oldPoint, newPoint);
                        movement.append(direction).append('e');
                        mListener.onPointEated(gamerId);
                        break;
                    default:
                        if ((newPointState & PACMAN_MASK) > 0) {
                        /*
                           пакмен в клетке поля:
                           - gamerId 8-15 биты
                           - id пакмена 0-7 биты
                        */
                            int gmId = (newPointState & SECOND_8_BIT) >> 8;
                            int pcmId = newPointState & FIRST_8_BIT;
                            if (gmId != gamerId) {
                                movement.append(direction)
                                        .append('e').append(gmId).append(',')
                                        .append(newPointState & FIRST_8_BIT);
                                updateFieldValue(newPoint, getFieldValue(oldPoint));
                                updatePoint(oldPoint, newPoint);
                                Point eated = mGamerIdUnits.get(gmId).get(pcmId);
                                //нет на поле
                                eated.x = -1;
                                eated.y = -1;
                                mListener.onPackmanEated(gamerId);
                            } else System.out.println("Уперся в своего " + movement);
                        }
                }
            }
            movement.append(';');
        }
        mListener.onPackmansMoved(gamerId, movement.toString());
    }

    private void updateFieldValue(int x, int y, int val) {
        field[y][x] = val;
    }

    private void updateFieldValue(Point p, int val) {
        updateFieldValue(p.x, p.y, val);
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

    /**
     * без геттеров и сеттеров меньше кода
     */
    @SuppressWarnings("InstanceVariableNamingConvention")
    public static class Point {
        int x;
        int y;

        @Override
        public String toString() {
            return x + "," + y;
        }
    }

}
