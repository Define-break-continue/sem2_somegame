package base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.executor.Executor;

import java.sql.SQLException;

/**
 * Created by vladislav
 */

public class TestsWithDB {
    protected static Executor executor;

    protected static final String TABLE_ADMINS = " `Admin` ";
    protected static final String TABLE_USER = " `User` ";
    protected static final String TABLE_STATISTICS = " `Score` ";

    @BeforeClass
    public static void createDB() throws SQLException {
        executor = new Executor(Main.RESOURCES_PATH + "/.cfg/testdb.json");
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_ADMINS)
                .append("(`email` VARCHAR(50) NOT NULL, ")
                .append("`isActivated` TINYINT(0) DEFAULT 0, ")
                .append("PRIMARY KEY (`email`)) ")
                .append("DEFAULT CHARACTER SET = utf8");
        executor.runUpdate(sql.toString());
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
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
        executor.runUpdate(sql.toString());
        sql.setLength(0);
        sql.append("CREATE TABLE IF NOT EXISTS")
                .append(TABLE_STATISTICS)
                .append("(`user_id` INT, ")
                .append("`games` INT UNSIGNED DEFAULT 0, ")
                .append("`wins` INT UNSIGNED DEFAULT 0, ")
                .append("`score` INT UNSIGNED DEFAULT 0, ")
                .append("PRIMARY KEY (`user_id`), ")
                .append("INDEX idx_score (`score`)) ")
                .append("DEFAULT CHARACTER SET = utf8");
        executor.runUpdate(sql.toString());
    }

    @After
    public void dropData() throws SQLException {
        executor.runUpdate("DELETE FROM " + TABLE_ADMINS);
        executor.runUpdate("DELETE FROM " + TABLE_USER);
        executor.runUpdate("DELETE FROM " + TABLE_STATISTICS);
    }

    @AfterClass
    public static void dropDB() throws SQLException {
        executor.runUpdate("DROP TABLE " + TABLE_ADMINS + ','
                + TABLE_STATISTICS + ',' + TABLE_USER);
    }

}
