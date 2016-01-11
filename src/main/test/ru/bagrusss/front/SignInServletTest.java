package ru.bagrusss.front;

import org.jetbrains.annotations.TestOnly;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by vladislav
 */
public class SignInServletTest {

    final AccountServiceFake accounts = mock(AccountServiceFake.class, RETURNS_DEEP_STUBS);
    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Test
    @TestOnly
    public void testDoGet() throws IOException, ServletException {
        when(request.getParameter("logout")).thenReturn("111").thenReturn(null);
        when(request.getSession().getId()).thenReturn("sess");
        SignInServlet signInServlet = new SignInServlet(accounts);
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            signInServlet.doGet(request, response);
            assertTrue(writer.toString().contains("status"));
            verify(accounts, times(1)).removeSession("sess");
            signInServlet.doGet(request, response);
            assertTrue(writer.toString().contains("Login"));
        }
    }

    @Test
    @TestOnly
    public void testDoPost() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("bagrusss").thenReturn(null);
        when(request.getParameter("password")).thenReturn("somepass").thenReturn(null);
        SignInServlet signInServlet = new SignInServlet(accounts);
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            UserProfile user = new UserProfile("bagrusss", "somepass", "bagrusss@gmail.com");
            when(accounts.getUser("bagrusss")).thenReturn(user);
            when(accounts.getUser(null)).thenReturn(null);
            signInServlet.doPost(request, response);
            assertTrue(writer.toString().contains("OK"));
            verify(accounts, times(1)).doSaveUser(eq(request), eq(user));
            signInServlet.doPost(request, response);
            assertTrue(writer.toString().contains("FAIL"));
        }
    }

}