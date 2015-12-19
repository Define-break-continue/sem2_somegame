package ru.bagrusss.servces.database.dao;

import ru.bagrusss.servces.database.executor.Executor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vladislav
 */

@SuppressWarnings("ALL")
public class AdminDAO {
    private Executor mExecutor = new Executor();
    public static final String TABLE_ADMINS = " `Admin` ";

    public AdminDAO() throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_ADMINS)
                .append("(`email` VARCHAR(50) NOT NULL, ")
                .append("`isActivated` TINYINT(0) DEFAULT 0, ")
                .append("PRIMARY KEY (`email`)) ")
                .append("DEFAULT CHARACTER SET = utf8");
        mExecutor.runUpdate(sql.toString());
        createAdmin("bagrusss@gmail.com", true);
    }

    public boolean isAdmin(String email) throws SQLException {
        StringBuilder sql = new StringBuilder()
                .append("SELECT `email` FROM")
                .append(TABLE_ADMINS).append("WHERE ")
                .append("`email`=\'").append(email)
                .append('\'');
        return mExecutor.runTypedQuery(sql.toString(), ResultSet::next);
    }

    public boolean createAdmin(String email, boolean isActivated) throws SQLException {
        if (new UserDAO().getUser(email, null) != null) {
            StringBuilder sql = new StringBuilder("INSERT IGNORE INTO ")
                    .append(TABLE_ADMINS).append("VALUES (\'")
                    .append(email).append("\', ")
                    .append(isActivated).append(')');
            return mExecutor.runUpdate(sql.toString()) != 0;
        }
        return false;
    }

}
