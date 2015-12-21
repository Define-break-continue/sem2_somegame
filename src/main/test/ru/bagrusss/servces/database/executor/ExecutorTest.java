package ru.bagrusss.servces.database.executor;

import base.TestsWithDB;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vladislav
 */

public class ExecutorTest extends TestsWithDB {

    @Rule
    public final ExpectedException sqlException = ExpectedException.none();

    @Test
    public void testExecutor() throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT IGNORE INTO ")
                .append(TABLE_USER).append("(`email`, `password`) ")
                .append("VALUES ('test@test.com', 'some password')");
        long id = executor.runUpdate(sql.toString(), rs -> {
            rs.next();
            return rs.getLong(1);
        });
        sql.setLength(0);
        sql.append("SELECT `id` FROM")
                .append(TABLE_USER).append("WHERE `id`=").append(id);
        long testId = executor.runTypedQuery(sql.toString(), rs -> {
            rs.next();
            return rs.getLong(1);
        });
        assertEquals(id, testId);
        sql.setLength(0);
        sql.append("INSERT IGNORE INTO ").append(TABLE_ADMINS).append("(`email`, `isActivated`) ")
                .append("VALUES ('test@test.com', 1)");
        assertTrue(executor.runUpdate(sql.toString()) > 0);
        sql.setLength(0);
        sql.append("INSERT IGNORE INTO ").append(TABLE_STATISTICS)
                .append("VALUES ('1', 0, 0, 0)");
        assertTrue(executor.runUpdate(sql.toString()) > 0);
        sql.setLength(0);
        sql.append("CREATE TABLE ").append("`Temp` (`id` INT NOT NULL)");
        executor.runUpdate(sql.toString());
        sql.setLength(0);
        sql.append("DROP TABLE `Temp`");
        executor.runUpdate(sql.toString());
        sql.append("I WANT SQLException!");
        sqlException.expect(SQLException.class);
        executor.runUpdate(sql.toString());
        executor.runUpdate(sql.toString(), rs -> true);
    }

}