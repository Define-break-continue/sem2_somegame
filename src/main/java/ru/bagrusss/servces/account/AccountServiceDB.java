package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.DataBaseService;
import ru.bagrusss.servces.database.ServiceDB;

import java.sql.SQLException;

/**
 * Created by vladislav
 */

public class AccountServiceDB implements AccountService {

    DataBaseService mDataBaseService = (DataBaseService) Main.getAppContext().get(DataBaseService.class);

    @Override
    public void removeAll() {

    }

    @Override
    public boolean isAdmin(String email) {
        return false;
    }

    @Override
    public long registerUser(@NotNull String email, @NotNull UserProfile userProfile) {
        return 0;
    }

    @Override
    public boolean addSession(@NotNull String sessionId, @NotNull UserProfile userProfile) {
        return false;
    }

    @Override
    public boolean removeSession(@NotNull String sessionId) {
        return false;
    }

    @Nullable
    @Override
    public UserProfile getUser(@NotNull String userName) {
        return null;
    }

    @Nullable
    @Override
    public UserProfile getSession(@NotNull String sessionId) {
        return null;
    }

    @Override
    public long getCountActivatedUsers() {
        return 0;
    }

    @Override
    public long getCountRegisteredUsers() {
        StringBuilder sql = new StringBuilder("SELECT COUNT(id) ")
                .append("FROM").append(ServiceDB.TABLE_USER);
        Long id;
        try {
            id = mDataBaseService.runTypedQuery(mDataBaseService.getConnection(), sql.toString(), result -> {
                long res = 0;
                if (result.next()) {
                    res = result.getLong(1);
                }
                return res;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
