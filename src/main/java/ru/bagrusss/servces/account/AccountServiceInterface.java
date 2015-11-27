package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vladislav on 12.10.15.
 */
public interface AccountServiceInterface {

    boolean isAdmin(String sessionId);

    boolean addUser(@NotNull String userName, @NotNull UserProfile userProfile);

    boolean addSession(@NotNull String sessionId, @NotNull UserProfile userProfile);

    boolean removeSession(@NotNull String sessionId);

    @Nullable
    UserProfile getUser(@NotNull String userName);

    @Nullable
    UserProfile getSession(@NotNull String sessionId);

    long getCountActivatedUsers();

    long getCountRegisteredUsers();
}
