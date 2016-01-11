package ru.bagrusss.front;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;
import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.servces.account.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * Created by vladislav
 */

public class SignUpServletTest {

    final AccountServiceFake accounts = AccountServiceFake.getInstance();
    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Test
    public void testDoGet() throws IOException, ServletException {
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            SignUpServlet signUpServlet = new SignUpServlet(accounts);
            signUpServlet.doGet(request, response);
            assertTrue(writer.toString().contains("Email"));
        }
    }

    @After
    public void clean() throws Exception {
        accounts.removeAll();
    }


    @Test
    public void testDoPost() throws IOException, ServletException {

        when(request.getParameter("email"))
                .thenReturn("bagrusss@gmail.com")
                .thenReturn("bag@gmail.com")
                .thenReturn(null);
        when(request.getParameter("password"))
                .thenReturn("somepass")
                .thenReturn("some")
                .thenReturn(null);
        when(request.getParameter("name"))
                .thenReturn("bagrusss")
                .thenReturn("bag")
                .thenReturn(null);
        when(request.getSession().getId()).thenReturn("sess").thenReturn("sess");
        UserProfile user = new UserProfile("bagrusss", "password", "bagrusss@gmail.com");
        UserProfile user1 = new UserProfile("www", "somepa", "bagrus@mail.com");
        accounts.addUser("bagrusss", user);
        accounts.addUser("www", user1);
        SignUpServlet signUpServlet = new SignUpServlet(accounts);
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            signUpServlet.doPost(request, response);
            assertEquals(accounts.getCountRegisteredUsers(), 2);
            signUpServlet.doPost(request, response);
            assertEquals(accounts.getCountRegisteredUsers(), 3);
        }
    }
}