package ru.bagrusss.apiservlets;

import com.google.gson.JsonElement;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vladislav
 */

public class Info extends BaseServlet {
    public static final String URL = "/info/*";

    public static final byte METHOD_SCOREBOARD = 10;
    public static final byte METHOD_USER = 11;

    private final String urlPattern = "/([1][01])(/)?";
    private final Pattern pattern = Pattern.compile(urlPattern);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte method = getMethod(req.getPathInfo(), pattern);
        switch (method) {
            case METHOD_SCOREBOARD:
                String count = req.getParameter(COUNT);
                long cnt = 0;
                try {
                    cnt = Long.valueOf(count);
                    if (cnt < 1)
                        throw new NumberFormatException("Incorrect value of parameter 'count'");
                } catch (NullPointerException | NumberFormatException e) {
                    Errors.errorAPI(resp, Errors.CODE_INVALID_REQUEST, e.getMessage());
                }
                List<ScoreDataSet> results = mResultsGame.getBestGamers(cnt);
                JsonElement jsonElement = mGson.toJsonTree(results);
                Errors.correct(resp, jsonElement.getAsJsonArray());
                break;
            case METHOD_USER:

                break;
            default:
                Errors.errorAPI(resp, Errors.CODE_INVALID_REQUEST, Errors.MESSAGE_INVALID_REQUEST);
        }
    }
}
