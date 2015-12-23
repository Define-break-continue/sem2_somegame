package ru.bagrusss.game.mechanics.field;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@SuppressWarnings("unused")
public class GameField {

    private static final byte DIRECTION_UP = 0;
    private static final byte DIRECTION_RIGHT = 1;
    private static final byte DIRECTION_DOWN = 2;
    private static final byte DIRECTION_LEFT = 3;

    private byte length;
    private byte height;

    private byte lastX;
    private byte lastY;


    private final int oldXmask = 0xff;
    private final int oldYmask = 0xff00;
    private final int newXmask = 0xff0000;
    private final int newYmask = 0xff000000;

    private final int oldY = 0x8;
    private final int newX = 0x16;
    private final int newY = 0x24;

    private ConcurrentHashMap<Byte, List<Short>> mGamerUnits;

    private EventsListener listener;

    private ConcurrentLinkedDeque<Object> mGamerMoves;

    public GameField(byte length, byte height) {
        this.length = length;
        this.height = height;
        lastX = (byte) (length - 1);
        lastY = (byte) (height - 1);
        mGamerMoves = new ConcurrentLinkedDeque<>();
        mGamerUnits = new ConcurrentHashMap<>();
    }

    public GameField(byte length, byte height, EventsListener listener) {
        this(length, height);
        this.listener = listener;
    }

    public void moveUnits(byte gamerId, byte direction) {
        List<Short> units = mGamerUnits.get(gamerId);
        short toNew = 0;
        byte move = 0;
        for (short cor : units)
            switch (direction) {
                case DIRECTION_UP:
                    toNew = (short) (cor - (1 << oldY));
                    break;
                case DIRECTION_RIGHT:
                    toNew = (short) (cor + 1);
                    break;
                case DIRECTION_LEFT:
                    toNew = (short) (cor - 1);
                    break;
                case DIRECTION_DOWN:
                    toNew = (short) (cor + (1 << oldY));
                    break;
                default:
                    return;
            }

    }

    private void move(short old, short tonew) {

    }
}
