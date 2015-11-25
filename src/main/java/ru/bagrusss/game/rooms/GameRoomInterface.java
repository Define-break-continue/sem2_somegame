package ru.bagrusss.game.rooms;

import org.jetbrains.annotations.NotNull;
import ru.bagrusss.models.UserProfile;

import java.util.List;

/**
 * Created by vladislav on 12.10.15.
 */
public interface GameRoomInterface {

    /**
     * Вызывается как пользователь вошел в комнату
     * @param usr - инфа о пользователе
     */
    void onUserJoin(@NotNull UserProfile usr);

    void onUserLeave(@NotNull UserProfile usr);

    void onUserChangeStatus(@NotNull UserProfile usr, boolean is_ready);

    void onAllUsersReady(@NotNull List<?> users);

}
