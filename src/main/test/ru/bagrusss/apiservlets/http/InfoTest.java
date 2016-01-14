package ru.bagrusss.apiservlets.http;

import base.BaseServletTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bagrusss.game.mechanics.ResultsGame;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.helpers.InitException;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by vladislav on 14.01.16.
 */
public class InfoTest extends BaseServletTest {

    final ResultsGame resultsGame = new Results();


    @Before
    public void before() {
        try {
            CONTEXT.add(ResultsGame.class, resultsGame);
        } catch (InitException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoGetInfo() throws IOException, ServletException {
        try (StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            Info info = new Info(CONTEXT);
            when(request.getPathInfo()).thenReturn("localhost/info/aaa/").
                    thenReturn("localhost/info/25/").thenReturn("localhost/info/10/");
            when(request.getParameter(Info.COUNT)).thenReturn("5").thenReturn("aaa")
                    .thenReturn(null).thenReturn("-2");
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            info.doGet(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_NOT_FOUND));
            stringWriter.flush();
            info.doGet(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_NOT_FOUND));
            stringWriter.flush();
            info.doGet(request, response);
            ScoreDataSet score = new ScoreDataSet(2, 2, 1, 9);
            Assert.assertTrue(stringWriter.toString().contains(String.valueOf(score.getScore())));
            stringWriter.flush();
            info.doGet(request, response);
            Assert.assertTrue(stringWriter.toString().contains(String.valueOf(Errors.CODE_INVALID_REQUEST)));
            stringWriter.flush();
            info.doGet(request, response);
            Assert.assertTrue(stringWriter.toString().contains(String.valueOf(Errors.CODE_INVALID_REQUEST)));
            stringWriter.flush();
            info.doGet(request, response);
            Assert.assertTrue(stringWriter.toString().contains(String.valueOf(Errors.CODE_INVALID_REQUEST)));
        }
    }

    static class Results implements ResultsGame {

        @Override
        public void saveResults(List<ScoreDAO.Score> results) {
        }

        @Override
        public ScoreDataSet getInfo(long userId) {
            return new ScoreDataSet(1, 2, 1, 5);
        }

        @Override
        public List<ScoreDataSet> getBestGamers(long count) {
            List<ScoreDataSet> list = new ArrayList<>(2);
            list.add(new ScoreDataSet(1, 2, 1, 5));
            list.add(new ScoreDataSet(2, 2, 1, 9));
            return list;
        }
    }

}