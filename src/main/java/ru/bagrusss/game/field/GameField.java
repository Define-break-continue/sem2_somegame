package ru.bagrusss.game.field;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.helpers.Resourses;

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

@SuppressWarnings("UNUSED")
public class GameField {

    public static final byte DIRECTION_UP = 3;
    public static final byte DIRECTION_RIGHT = 0;
    public static final byte DIRECTION_DOWN = 1;
    public static final byte DIRECTION_LEFT = 2;

    private static final Logger LOG = Logger.getLogger(GameField.class.getName());

    private byte length;
    private byte height;

    private byte lastX;
    private byte lastY;

    private static final int FIRST_8_BIT = 0XFF;
    private static final int SECOND_8_BIT = FIRST_8_BIT << 8;

    private static final int TO_SECOND_8_BIT = 8;

    private static final int PACMAN_MASK = 0x0100FFFF;
    private static final int PACMAN_BIT = 0x01000000;

    private static final int WALL = 0x2000000;
    private static final int POINT = 0x3000000;
    private static final int BONUS_MASK = 0x400FFFF;
    private static final int EMPTY = 0;

    private short maxPoints;
    private byte pacmans;
    private byte walls;


    /**
     * без геттеров и сеттеров проще
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

    private int[][] field;

    private ConcurrentHashMap<Byte, List<Point>> mGamerUnits;

    private EventsListener listener;

    public GameField(String configs, @NotNull EventsListener listener) {
        JsonObject mConfigs = getFieldConfigs(configs);
        try {
            this.length = mConfigs.get("length").getAsByte();
            this.height = mConfigs.get("height").getAsByte();
            maxPoints = mConfigs.get("points").getAsShort();
            pacmans = mConfigs.get("gamerPackmans").getAsByte();
            walls = mConfigs.get("walls").getAsByte();
        } catch (NullPointerException e) {
            LOG.log(Level.SEVERE, "Incorrect configs in file " + configs);
        }
        field = new int[height][length];
        lastX = (byte) (length - 1);
        lastY = (byte) (height - 1);
        mGamerUnits = new ConcurrentHashMap<>();
        this.listener = listener;
    }

    public JsonObject getFieldConfigs(String path) {
        try {
            return Resourses.readResourses(path, JsonObject.class);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, this.getClass().getName(), e);
            return new JsonObject();
        }
    }

    public void prepareFieldToGame(@NotNull List<Byte> gamerIds) {
        generateWalls(walls);
        generatePoints(maxPoints);
        generatePackmans(gamerIds);
    }

    private void generateWalls(short count) {

    }

    private void generatePackmans(@NotNull List<Byte> gamerIds) {
        for (byte gmId : gamerIds) {
            byte remaindGeneratePcms = (byte) (pacmans - 1);
            List<Point> packmans = new ArrayList<>(pacmans);
            while (remaindGeneratePcms >= 0) {
                Point p = Generator.genetatePoint(0, lastX, 0, lastY);
                int val = getFieldValue(p);
                if (val == 0) {
                    updateFieldValue(p, (gmId << TO_SECOND_8_BIT) + remaindGeneratePcms + PACMAN_BIT);
                    packmans.add(p);
                    --remaindGeneratePcms;
                }
            }
            mGamerUnits.put(gmId, packmans);
        }
    }

    private void generatePoints(short count) {
        while (count > 0) {
            Point p = Generator.genetatePoint(0, lastX, 0, lastY);
            if (getFieldValue(p) == 0) {
                updateFieldValue(p, POINT);
                --count;
            }
        }
    }

    public void moveUnits(byte gamerId, byte direction) {
        List<Point> units = mGamerUnits.get(gamerId);
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
                    case POINT:
                        updateFieldValue(newPoint, getFieldValue(oldPoint));
                        updatePoint(oldPoint, newPoint);
                        movement.append(direction).append('e');
                        listener.onPointEated(gamerId);
                        break;
                    default:
                        if ((newPointState & PACMAN_MASK) > 0) {
                        /*
                           пакмен в клетке поля:
                           - gamerId 8-15 биты
                           - id пакмена 0-7 биты
                        */
                            byte gmId = (byte) ((newPointState & SECOND_8_BIT) >> 8);
                            byte pcmId = (byte) (newPointState & FIRST_8_BIT);
                            if (gmId != gamerId) {
                                movement.append(direction)
                                        .append('e').append(gmId).append(',')
                                        .append((byte) (newPointState & FIRST_8_BIT));
                                updateFieldValue(newPoint, getFieldValue(oldPoint));
                                updatePoint(oldPoint, newPoint);
                                Point pcm = mGamerUnits.get(gmId).get(pcmId);
                                //нет на поле
                                pcm.x = -1;
                                pcm.y = -1;
                                listener.onPackmanEated(gamerId);
                            } else System.out.println("Уперся в своего " + movement);
                        }
                }
            }
            movement.append(';');
        }
        listener.onPackmansMoved(gamerId, movement.toString());
    }

    private void updateFieldValue(int x, int y, int val) {
        ;
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

}
