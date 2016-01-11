package ru.bagrusss.apiservlets.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.apiservlets.http.BaseServlet;
import ru.bagrusss.game.mechanics.GameMechanicsService;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.account.AccountService;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import javax.servlet.http.Cookie;

/**
 * Created by vladislav
 */

public class GameWebSocketCreator implements WebSocketCreator {

    private final AccountService mAcService = (AccountService) Main.getAppContext().get(AccountService.class);
    private final GameMechanicsService mGMService = (GameMechanicsService)
            Main.getAppContext().get(GameMechanicsService.class);

    @Nullable
    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        Cookie[] cookies = req.getHttpServletRequest().getCookies();
        Cookie cookie = BaseServlet.getCookieByName(cookies, BaseServlet.ACCESS_TOKEN);
        assert cookie != null;
        String key = cookie.getValue();
        UserDataSet usr = mAcService.getSession(key);
        assert usr != null;
        return mGMService.hasPlaces() && mGMService.getStatus() != GameMechanicsService.STATUS_PLAY ?
                new GameWebSocket(usr, mGMService.generateGameId()) : null;
    }

}
