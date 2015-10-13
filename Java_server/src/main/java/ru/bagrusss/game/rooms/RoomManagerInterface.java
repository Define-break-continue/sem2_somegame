package ru.bagrusss.game.rooms;

import org.jetbrains.annotations.NotNull;

/**
 * Created by vladislav on 12.10.15.
 */

public interface RoomManagerInterface {

    void onRoomCreated(@NotNull GameRoom room);

    void onRoomEdited(@NotNull GameRoom room);

    void onRoomRemoved(@NotNull GameRoom room);

}
