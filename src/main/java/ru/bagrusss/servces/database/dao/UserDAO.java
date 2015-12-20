package ru.bagrusss.servces.database.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.servces.database.dataset.UserDataSet;
import ru.bagrusss.servces.database.executor.Executor;

import java.sql.SQLException;

/**
 * Created by vladislav
 */

@SuppressWarnings("all")
public class UserDAO {

    private Executor mExecutor = new Executor();

    public static final String TABLE_USER = " `User` ";

    public UserDAO() throws SQLException {
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
        mExecutor.runUpdate(new StringBuilder("INSERT IGNORE INTO")
                .append(TABLE_USER).append("(`email`, `password`) ")
                .append("VALUES (\'bagrusss@gmail.com\', ")
                .append("\'*C9D46B2D5519BD0FD0A98CD0255FD9261A4E15C9\')").toString());
    }

    @Nullable
    public UserDataSet getUser(@NotNull String email, @Nullable String pass) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT `id`, `email` FROM")
                .append(TABLE_USER).append("WHERE `email`=\'")
                .append(email).append("\' ");
        if (pass != null) {
            sql.append("AND `password`=PASSWORD('")
                    .append(pass).append("\')");
        }
        return mExecutor.runTypedQuery(sql.toString(),
                rs -> rs.next() ? new UserDataSet(rs.getLong(1), rs.getString(2)) : null);
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

    @Nullable
    public UserDataSet getUserById(long id) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT `id`, `email` FROM")
                .append(TABLE_USER).append("WHERE `id`=\'")
                .append(id);
        return mExecutor.runTypedQuery(sql.toString(),
                rs -> rs.next() ? new UserDataSet(rs.getLong(1), rs.getString(2)) : null);
    }

    public long getUserCount() throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(`id`) FROM")
                .append(TABLE_USER);
        return mExecutor.runTypedQuery(sql.toString(),
                rs -> rs.next() ? rs.getLong(1) : 0);
    }
}
