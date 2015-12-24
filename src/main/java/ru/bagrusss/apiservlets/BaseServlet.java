package ru.bagrusss.apiservlets;

import com.google.gson.Gson;
import ru.bagrusss.game.ResultsGame;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.main.Context;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.account.AccountService;

import javax.servlet.http.HttpServlet;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vladislav
 */


@SuppressWarnings("ConstantNamingConvention")
public class BaseServlet extends HttpServlet {

    protected static final String PASSWORD1 = "password1";
    protected static final String PASSWORD2 = "password2";
    protected static final String PASSWORD = "password";
    protected static final String EMAIL = "email";
    protected static final String TYPE = "tp";
    protected static final String ID = "id";
    protected static final String COUNT = "count";
    protected static final String ACCESS_TOKEN = "access_token";

    public static final String JSON_TYPE = "application/json; charset=utf-8";

    protected final Gson mGson = Resourses.GSON;
    protected final Logger log = Logger.getLogger(getClass().getCanonicalName());
    protected final Context mContext = Main.getAppContext();
    protected final AccountService mAccountService = (AccountService) mContext.get(AccountService.class);
    protected final ResultsGame mResultsGame = (ResultsGame) mContext.get(ResultsGame.class);

    public static final String DEFAULT_ENCODING = "UTF-8";

    protected final SecureRandom secureRandom = new SecureRandom();

    private static final int KEYLENGTH = 40;
    private static final int KEYNUMB = 128;

    protected String generateKey() {
        return new BigInteger(KEYNUMB, secureRandom).toString(KEYLENGTH);
    }

    protected byte getMethod(String url, Pattern pattern) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? Byte.valueOf(matcher.group(1)) : 0;
    }

}
