package ru.bagrusss.servces.account;

import org.jetbrains.annotations.TestOnly;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by vladislav
 */
public class AccountServiceFakeTest {

    AccountServiceFake mAccountService;
    List<UserProfile> mUsers;

    @Before
    public void setUp() throws Exception {
        mAccountService = new AccountServiceFake();
        mUsers = new LinkedList<>();
        mUsers.add(new UserProfile("vlad1", "123", "vlad@mail.ru"));
        mUsers.add(new UserProfile("vlad2", "1232", "vlad1@mail.ru"));
        mUsers.add(new UserProfile("vlad3", "some", "vlad2@mail.ru"));
        mUsers.add(new UserProfile("vlad4", "456", "vlad4@mail.ru"));
    }

    @After
    public void tearDown() throws Exception {
        mAccountService.removeAll();
        mUsers.clear();
    }

    @TestOnly
    @Test
    public void testAddUser() throws Exception {
        for (UserProfile mUser : mUsers) {
            mAccountService.registerUser(mUser.getmUserLogin(), mUser);
        }
        assertFalse(mAccountService.registerUser("vlad3", new UserProfile("vlad3", "456", "vlad3@mail.ru"))>0);
    }

    @TestOnly
    @Test
    public void testGetUser() throws Exception {
        for (UserProfile usr : mUsers) {
            mAccountService.registerUser(usr.getmUserLogin(), usr);
            assertEquals(mAccountService.getUser(usr.getmUserLogin()), usr);
        }
        assertEquals(null, mAccountService.getUser("vlad5"));
    }

    @TestOnly
    @Test
    public void testAddSession() throws Exception {
        for (UserProfile usr : mUsers) {
            mAccountService.addSession(usr.toString(), usr);
        }
        assertEquals(4, mAccountService.getCountActivatedUsers());
        assertFalse(mAccountService.addSession(mUsers.get(0).toString(), mUsers.get(0)));
    }

    @TestOnly
    @Test
    public void testGetSession() throws Exception {
        assertNull(mAccountService.getSession(mUsers.get(0).toString()));
        for (UserProfile usr : mUsers) {
            mAccountService.addSession(usr.toString(), usr);
            assertNotNull(mAccountService.getSession(usr.toString()));
        }
    }

    @TestOnly
    @Test
    public void testRemoveSession() throws Exception {
        for (UserProfile usr : mUsers) {
            mAccountService.addSession(usr.toString(), usr);
        }
        assertFalse(mAccountService.removeSession(new UserProfile(" ", " ", " ").toString()));
        for (UserProfile usr : mUsers) {
            assertTrue(mAccountService.removeSession(usr.toString()));
        }
    }

    @TestOnly
    @Test
    public void testDoSaveUser() throws Exception {
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getId()).thenReturn("somesession");

        HttpServletRequest mock = mock(HttpServletRequest.class);
        when(mock.getSession()).thenReturn(mockSession);
        mAccountService.addSession(mockSession.getId(), mUsers.get(0));
        assertEquals(1, mAccountService.getCountActivatedUsers());
        mAccountService.addSession(mockSession.getId(), mUsers.get(0));
        assertEquals(mAccountService.getCountActivatedUsers(), 1);
    }

    @TestOnly
    @Test
    public void testRemoveAll() throws Exception {
        for (UserProfile usr : mUsers) {
            mAccountService.registerUser(usr.getmUserLogin(), usr);
            mAccountService.addSession(usr.toString(), usr);
        }
        mAccountService.removeAll();
        assertEquals(0, mAccountService.getCountActivatedUsers());
        assertEquals(0, mAccountService.getCountRegisteredUsers());
    }
}