package ru.bagrusss.servces.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vladislav on 14.12.15.
 */
public interface TResultHandler<T> {
    T handle(ResultSet rs) throws SQLException;
}
