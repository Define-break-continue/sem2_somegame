package ru.bagrusss.game.rooms;

import org.jetbrains.annotations.NotNull;
import ru.bagrusss.models.UserProfile;

import java.util.List;

/**
 * Created by vladislav on 12.10.15.
 */

public class GameRoom implements GameRoomInterface {

    @Override
    public void onUserJoin(@NotNull UserProfile usr) {

    }

    @Override
    public void onUserLeave(@NotNull UserProfile usr) {

    }

    @Override
    public void onUserChangeStatus(@NotNull UserProfile usr, boolean is_ready) {

    }


    @Override
    public void onAllUsersReady(@NotNull List<?> users) {

    }

}
