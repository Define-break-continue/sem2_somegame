package ru.bagrusss.apiservlets.http;

import com.google.gson.Gson;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.game.mechanics.ResultsGame;
import ru.bagrusss.helpers.Resources;
import ru.bagrusss.main.Context;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.account.AccountService;

import javax.servlet.http.Cookie;
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

    public static final String PASSWORD1 = "password1";
    public static final String PASSWORD2 = "password2";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String TYPE = "tp";
    public static final String ID = "id";
    public static final String COUNT = "count";
    public static final String ACCESS_TOKEN = "access_token";

    public static final String JSON_TYPE = "application/json; charset=utf-8";

    protected final Gson mGson = Resources.GSON;
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

    @Nullable
    public static Cookie getCookieByName(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name))
                return cookie;
        }
        return null;
    }

}
