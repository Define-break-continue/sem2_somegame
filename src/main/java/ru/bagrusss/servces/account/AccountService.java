package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vladislav
 */

public interface AccountService {

    void removeAll();

    boolean addUser(@NotNull String userName, @NotNull UserProfile userProfile);

    boolean addSession(@NotNull String sessionId, @NotNull UserProfile userProfile);

    boolean removeSession(@NotNull String sessionId);

    @Nullable
    UserProfile getUser(String userName);

    @Nullable
    UserProfile getSession(@NotNull String sessionId);

    long getCountActivatedUsers();

    long getCountRegisteredUsers();
}
