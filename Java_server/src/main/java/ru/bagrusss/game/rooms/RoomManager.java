package ru.bagrusss.game.rooms;

import org.jetbrains.annotations.NotNull;

/**
 * Используется для управления существующими игровыми комнатами
 * синглтон
 */
public class RoomManager implements RoomManagerInterface {

    private static RoomManager s_mRoomManager;
    private byte mGameStatus;

    public static RoomManager getInstance(){
        RoomManager localInstance = s_mRoomManager;
        if (localInstance == null) {
            synchronized (RoomManager.class) {
                localInstance = s_mRoomManager;
                if (localInstance == null) {
                    s_mRoomManager = localInstance = new RoomManager();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void onRoomCreated(@NotNull GameRoom room) {

    }

    @Override
    public void onRoomEdited(@NotNull GameRoom room) {

    }

    @Override
    public void onRoomRemoved(@NotNull GameRoom room) {

    }
}
