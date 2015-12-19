package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.helpers.BaseInterface;
import ru.bagrusss.servces.database.dataset.UserDataSet;

/**
 * Created by vladislav
 */

public interface AccountService extends BaseInterface {

    void removeAll();

    boolean isAdmin(String email);

    long registerUser(@NotNull String email, @NotNull UserDataSet user);

    boolean addSession(@NotNull String sessionId, @NotNull UserDataSet user);

    boolean removeSession(@NotNull String sessionId);

    @Nullable
    UserDataSet getUser(@NotNull String email);

    @Nullable
    UserDataSet getUser(@NotNull String email, @NotNull String password);

    @Nullable
    UserDataSet getSession(@NotNull String sessionId);

    int getCountActivatedUsers();

    long getCountRegisteredUsers();
}
