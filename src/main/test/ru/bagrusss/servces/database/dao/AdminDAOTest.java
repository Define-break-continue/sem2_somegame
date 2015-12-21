package ru.bagrusss.servces.database.dao;

import base.TestsWithDB;
import org.junit.Test;
import ru.bagrusss.servces.database.dataset.AdminDataSet;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by vladislav on 21.12.15.
 */

public class AdminDAOTest extends TestsWithDB {

    private final UserDAO mUserDAO = new UserDAO(executor);
    private final AdminDAO mAdminDAO = new AdminDAO(executor);

    @Test
    public void testAdminDAO() throws SQLException {
        mUserDAO.insertUser(new UserDataSet("testadmin@mail.ru", "somepass"));
        mUserDAO.insertUser(new UserDataSet("testadmin2@mail.ru", "somepass"));
        AdminDataSet admin = new AdminDataSet("testadmin@mail.ru", true);
        assertTrue(mAdminDAO.createAdmin("testadmin@mail.ru", true));
        assertTrue(mAdminDAO.createAdmin("testadmin2@mail.ru", false));
        assertFalse(mAdminDAO.createAdmin("testadmin3@mail.ru", false));
        assertTrue(mAdminDAO.isAdmin("testadmin2@mail.ru"));
        assertFalse(mAdminDAO.isAdmin("testadmin3@mail.ru"));
        assertEquals(admin, mAdminDAO.getAdminByEmail("testadmin@mail.ru"));
    }

}