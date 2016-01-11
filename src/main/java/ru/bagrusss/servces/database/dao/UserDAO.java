package ru.bagrusss.servces.database.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.helpers.Resources;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.dataset.UserDataSet;
import ru.bagrusss.servces.database.executor.Executor;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by vladislav
 */

@SuppressWarnings({"StringBufferReplaceableByString"})
public class UserDAO {

    private final Executor mExecutor;

    public static final String TABLE_USER = " `User` ";

    public UserDAO(@NotNull Executor mExecutor) {
        this.mExecutor = mExecutor;
    }

    public void createUserTable() throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_USER)
                .append("(`id` INT NOT NULL AUTO_INCREMENT, ")
                .append("`email` VARCHAR(50), ")
                .append("`first_name` VARCHAR(35) DEFAULT NULL, ")
                .append("`last_name` VARCHAR(35) DEFAULT NULL, ")
                .append("`password` VARCHAR(50) NOT NULL, ")
                .append("PRIMARY KEY (`email`), ")
                .append("KEY email_pass (`email`, `password`), ")
                .append("UNIQUE KEY key_id(`id`)) ")
                .append("DEFAULT CHARACTER SET = utf8");
        mExecutor.runUpdate(sql.toString());
        JsonArray users = null;
        try {
            users = Resources.readResourses(Main.RESOURCES_PATH + "/.cfg/admins.json", JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(Main.CONFIGS_ERROR);
        }
        for (JsonElement usr : users) {
            mExecutor.runUpdate(new StringBuilder("INSERT IGNORE INTO")
                    .append(TABLE_USER).append("(`email`, `password`) ")
                    .append("VALUES (\'")
                    .append(usr.getAsJsonObject().get("email").getAsString()).append("\', \'")
                    .append(usr.getAsJsonObject().get("passhash").getAsString())
                    .append("\')").toString());
        }
    }

    public UserDataSet getUser(@NotNull String email, @Nullable String pass) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT `id`, `email` FROM")
                .append(TABLE_USER).append("WHERE `email`=\'")
                .append(email).append("\' ");
        if (pass != null) {
            sql.append("AND `password`=PASSWORD('")
                    .append(pass).append("\')");
        }
        return mExecutor.runTypedQuery(sql.toString(), rs -> {
            rs.next();
            return new UserDataSet(rs.getLong(1), rs.getString(2));
        });
    }

    public long insertUser(@NotNull UserDataSet user) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT IGNORE INTO")
                .append(TABLE_USER).append("(`email`, `password`) ")
                .append(" VALUES (\'").append(user.getEmail())
                .append("\', PASSWORD(\'").append(user.getPassword())
                .append("'))");
        return mExecutor.runUpdate(sql.toString(),
                rs -> rs.next() ? rs.getLong(1) : 0);
    }

    public UserDataSet getUserById(long id) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM")
                .append(TABLE_USER).append("WHERE `id`=")
                .append(id);
        return mExecutor.runTypedQuery(sql.toString(), rs -> {
            rs.next();
            return new UserDataSet(rs.getLong(1), rs.getString(2), rs.getString(5),
                    rs.getString(3), rs.getString(4));
        });
    }

    public long getUserCount() throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(`id`) FROM")
                .append(TABLE_USER);
        return mExecutor.runTypedQuery(sql.toString(),
                rs -> rs.next() ? rs.getLong(1) : 0);
    }

    public boolean updateUser(@NotNull UserDataSet user) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE").append(TABLE_USER)
                .append("SET `first_name`= \'").append(user.getFirstname())
                .append("\', `last_name`= \'").append(user.getLastname())
                .append("\' WHERE `email`=\'").append(user.getEmail())
                .append('\'');
        return mExecutor.runUpdate(sql.toString()) > 0;
    }

    public void deleteAll() throws SQLException {
        mExecutor.runUpdate("DELETE FROM " + TABLE_USER);
    }
}