package ru.bagrusss.administration;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * Created by vladislav
 */

public class AdminPageServletTest {

    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
/*
    @Rule
    public final ExpectedException thrown = ExpectedException.none();*/

    @Test
    public void testDoGet() throws IOException, ServletException {
        when(request.getParameter("shutdown"))
                .thenReturn("1000")
                .thenReturn("abs")
                .thenReturn(null);
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            AdminPageServlet adminPageServlet = new AdminPageServlet();
            adminPageServlet.doGet(request, response);
            assertTrue(writer.toString().contains(AdminPageServlet.STATUS_STOPPING));
            writer.flush();
            adminPageServlet.doGet(request, response);
            assertTrue(writer.toString().contains(AdminPageServlet.STATUS_STOP_ERROR))
            ;writer.flush();
            adminPageServlet.doGet(request, response);
            assertTrue(writer.toString().contains(AdminPageServlet.STATUS_RUN));
        }
    }
}