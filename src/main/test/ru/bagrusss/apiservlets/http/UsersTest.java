package ru.bagrusss.apiservlets.http;

import base.BaseServletTest;
import org.junit.Assert;
import org.junit.Test;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

/**
 * Created by vladislav on 14.01.16.
 */
public class UsersTest extends BaseServletTest {

    @Test
    public void testDoPost() throws IOException, ServletException {
        try (StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            Users users = new Users(CONTEXT);
            UserDataSet user = new UserDataSet("some@mail.ri", "pass");
            ACCOUNTSERVICE.registerUser("some@mail.ri", user);
            ACCOUNTSERVICE.addSession("somesess", user);
            when(request.getPathInfo()).thenReturn("localhost/users/aaa/").
                    thenReturn("localhost/users/10/").thenReturn("localhost/users/22/");
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("somesess");
            users.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_NOT_FOUND));
            stringWriter.flush();
            users.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_NOT_FOUND));
            stringWriter.flush();
            users.doPost(request, response);
            Assert.assertEquals(ACCOUNTSERVICE.getCountActivatedUsers(), 0);
            users.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_USER_NOT_EXISTS));
        }
    }
}