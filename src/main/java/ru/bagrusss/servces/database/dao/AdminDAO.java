package ru.bagrusss.servces.database.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.dataset.AdminDataSet;
import ru.bagrusss.servces.database.executor.Executor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vladislav
 */

@SuppressWarnings({"StringBufferReplaceableByString", "unused"})
public class AdminDAO {

    private final Executor mExecutor;
    public static final String TABLE_ADMINS = " `Admin` ";

    public AdminDAO(@NotNull Executor ex) {
        this.mExecutor = ex;
    }

    public void createAdminTable() throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_ADMINS)
                .append("(`email` VARCHAR(50) NOT NULL, ")
                .append("`isActivated` TINYINT(0) DEFAULT 0, ")
                .append("PRIMARY KEY (`email`)) ")
                .append("DEFAULT CHARACTER SET = utf8");
        mExecutor.runUpdate(sql.toString());
        JsonArray admins = null;
        try {
            admins = Resourses.readResourses(Main.RESOURCES_PATH + "/.cfg/admins.json", JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(Main.CONFIGS_ERROR);
        }
        for (JsonElement adm : admins) {
            createAdmin(adm.getAsJsonObject().get("email").getAsString(), true);
        }
    }

    public boolean isAdmin(@NotNull String email) throws SQLException {
        StringBuilder sql = new StringBuilder()
                .append("SELECT `email` FROM")
                .append(TABLE_ADMINS).append("WHERE ")
                .append("`email`=\'").append(email)
                .append('\'');
        return mExecutor.runTypedQuery(sql.toString(), ResultSet::next);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean createAdmin(@NotNull String email, boolean isActivated) throws SQLException {
        if (new UserDAO(mExecutor).getUser(email, null) != null) {
            StringBuilder sql = new StringBuilder("INSERT IGNORE INTO ")
                    .append(TABLE_ADMINS).append("VALUES (\'")
                    .append(email).append("\', ")
                    .append(isActivated).append(')');
            return mExecutor.runUpdate(sql.toString()) != 0;
        }
        return false;
    }

    public AdminDataSet getAdminByEmail(@NotNull String email) throws SQLException {
        StringBuilder sql = new StringBuilder()
                .append("SELECT `isActivated` FROM")
                .append(TABLE_ADMINS).append("WHERE ")
                .append("`email`=\'").append(email)
                .append('\'');
        return mExecutor.runTypedQuery(sql.toString(), rs -> {
            rs.next();
            return new AdminDataSet(email, rs.getBoolean(1));
        });
    }

}
