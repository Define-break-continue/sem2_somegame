package ru.bagrusss.frontend;

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

public class SignUpServletTest {

    final AccountServiceFake accounts = mock(AccountServiceFake.class, RETURNS_DEEP_STUBS);
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

    @Test
    public void testDoPost() throws IOException, ServletException {
        try (final StringWriter writer = new StringWriter()) {
            when(request.getParameter("email"))
                    .thenReturn("bagrusss@gmail.com")
                    .thenReturn("bagrusss@gmail.com")
                    .thenReturn(null);
            when(request.getParameter("password"))
                    .thenReturn("somepass")
                    .thenReturn("somepass")
                    .thenReturn(null);
            when(request.getParameter("name"))
                    .thenReturn("bagrusss")
                    .thenReturn("bagrusss")
                    .thenReturn(null);
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            UserProfile userProfile = new UserProfile("bagrusss", "somepass", "bagrusss@gmail.com");
            SignUpServlet signUpServlet = new SignUpServlet(accounts);
            when(accounts.addUser("bagrusss@gmail.com", userProfile)).thenReturn(true).thenReturn(false);
            signUpServlet.doPost(request, response);
            verify(accounts, times(1)).doSaveUser(eq(request), eq(userProfile));
            assertTrue(writer.toString().contains("OK"));
            writer.flush();
            signUpServlet.doPost(request, response);
            assertTrue(writer.toString().contains("FAIL"));
            writer.flush();
            signUpServlet.doPost(request, response);
            assertTrue(writer.toString().contains("FAIL"));
        }
    }
}