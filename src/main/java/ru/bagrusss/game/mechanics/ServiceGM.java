package ru.bagrusss.game.mechanics;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.bagrusss.apiservlets.websocket.WebSocketService;
import ru.bagrusss.game.field.GameField;
import ru.bagrusss.helpers.InitException;
import ru.bagrusss.helpers.Resources;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vladislav
 */

@SuppressWarnings({"unused"})
public class ServiceGM implements GameMechanicsService {

    private static final String GAMER_ID = "gamerId";
    private static final String BG_COLOR = "bg_color";
    private static final String LN_COLOR = "ln_color";
    private static final String LENGTH = "length";
    private static final String HEIGHT = "height";
    private static final String POINTS = "points";
    private static final String GAMER_PACKMANS = "gamerPackmans";
    private static final String WALLS = "walls";
    private static final String COLOR = "color";
    private static final String UNITS = "packmans";
    private static final String INIT_MSG = "Incorrect colors count. See: \'resourses/.data/field.json\'";

    private final ConcurrentSkipListMap<Integer, UserDataSet> mGameIdUser;
    private final ConcurrentHashMap<Integer, ScoreDAO.Score> mGameIdScore;
    private final WebSocketService mWSS = (WebSocketService) Main.getAppContext()
            .get(WebSocketService.class);
    private final ResultsGame mRG = (ResultsGame) Main.getAppContext()
            .get(ResultsGame.class);
    private final AtomicInteger mFreePlaces = new AtomicInteger(5);
    private final AtomicInteger mLastId = new AtomicInteger(1);
    ServiceGM mGM = this;
    private String[] mUnitsColorsBody;
    private String[] mUnitsColorsLine;
    private byte mMaxWalls;
    private short mMaxPoints;
    private byte mMaxPacmans;
    private byte mLength;
    private byte mHeight;
    private byte mStatus = STATUS_WAIT;
    private GameField mGameField;
    private Timer mTimer;
    private TimerTask timerTask;

    public ServiceGM() {
        mGameIdUser = new ConcurrentSkipListMap<>();
        mGameIdScore = new ConcurrentHashMap<>();
    }

    public void confugure(String path) throws IOException, InitException {
        JsonObject conf = Resources.readResourses(path, JsonObject.class);
        try {
            mLength = conf.get(LENGTH).getAsByte();
            mHeight = conf.get(HEIGHT).getAsByte();
            mMaxPoints = conf.get(POINTS).getAsShort();
            mMaxPacmans = conf.get(GAMER_PACKMANS).getAsByte();
            mMaxWalls = conf.get(WALLS).getAsByte();
            JsonArray colors = conf.get(COLOR).getAsJsonArray();
            int size = colors.size();
            if (size < 5)
                throw new InitException(INIT_MSG);
            mUnitsColorsBody = new String[size];
            mUnitsColorsLine = new String[size];
            for (int i = 0; i < size; ++i) {
                JsonObject jo = colors.get(i).getAsJsonObject();
                mUnitsColorsBody[i] = jo.get(BG_COLOR).getAsString();
                mUnitsColorsLine[i] = jo.get(LN_COLOR).getAsString();
            }
        } catch (NullPointerException e) {
            System.exit(Main.CONFIGS_ERROR);
        }
        mGameField = new GameField(mLength, mHeight, this);
        mGameField.setMaxWalls(mMaxWalls);
        mGameField.setMaxPoints(mMaxPoints);
        mGameField.setMaxPacmansForGamer(mMaxPacmans);
    }

    @Override
    public JsonObject getGameFieldState() {
        JsonObject jsonObject = new JsonObject();
        JsonArray units = new JsonArray();
        jsonObject.add(UNITS, units);
        for (int key : mGameIdUser.keySet()) {
            JsonObject unit = new JsonObject();
            unit.addProperty(GAMER_ID, key);
            unit.addProperty(BG_COLOR, mUnitsColorsBody[key]);
            unit.addProperty(LN_COLOR, mUnitsColorsLine[key]);
            unit.addProperty(UNITS, mGameField.getGamerUnits(key));
            unit.addProperty(UNITS, mGameField.getGamerUnits(key));
            units.add(unit);
        }
        jsonObject.addProperty(POINTS, mGameField.getFieldObjects(GameField.POINT));
        jsonObject.addProperty(WALLS, mGameField.getFieldObjects(GameField.WALL));
        return jsonObject;
    }

    @Override
    public boolean joinToGame(UserDataSet user, int gamerId) {
        if (mFreePlaces.get() - 1 == 0 && mStatus == STATUS_PLAY) {
            return false;
        }
        mGameIdUser.put(gamerId, user);
        ScoreDAO.Score score = new ScoreDAO.Score(gamerId, false, 0);
        score.setId(mGameIdUser.get(gamerId).getId());
        mGameIdScore.put(gamerId, score);
        if (mGameIdUser.size() >= MIN_USERS_FOR_GAME) {
            mStatus = STATUS_WAIT_START;
            startTimer(TIME_WAIT_GAME);
        }
        mFreePlaces.decrementAndGet();
        return true;
    }

    private void startTimer(long ms) {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ms == TIME_WAIT_GAME)
                    startGame(ms);
                else gameOver();
            }
        }, ms);
    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void leaveGame(int gamerId) {
        ScoreDAO.Score score = mGameIdScore.remove(gamerId);
        mGameIdUser.remove(gamerId);
        mLastId.decrementAndGet();
        mFreePlaces.incrementAndGet();
        if (mStatus == STATUS_WAIT_START && mGameIdUser.size() < MIN_USERS_FOR_GAME) {
            mStatus = STATUS_WAIT;
            stopTimer();
        }
        if (mStatus == STATUS_PLAY && mGameIdScore.size() > MIN_USERS_FOR_GAME) {
            List<ScoreDAO.Score> scores = new ArrayList<>(1);
            scores.add(score);
            mRG.saveResults(scores);
        }
        if (mStatus == STATUS_PLAY && mGameIdUser.size() < MIN_USERS_FOR_GAME) {
            stopTimer();
            gameOver();
        }
    }

    @Override
    public void moveGamerUnits(int gamerId, byte dir) {
        mGameField.moveUnits(gamerId, dir);
    }

    void startGame(long gameTimeMS) {
        mGameField.prepareFieldToGame(new ArrayList<>(mGameIdUser.keySet()));
        mWSS.notifyStartGame(gameTimeMS);
    }

    void gameOver() {
        List<ScoreDAO.Score> results = new ArrayList<>(mGameIdScore.values());
        if (results.size() > 1)
            Collections.sort(results, (o1, o2) -> {
                long score1 = o1.getScore();
                long score2 = o2.getScore();
                if (score1 == score2)
                    return 0;
                return score1 > score2 ? 1 : -1;
            });
        ScoreDAO.Score winScore = results.get(0);
        winScore.setWin(true);
        int winId = 0;
        for (Integer id : mGameIdScore.keySet()) {
            if (mGameIdScore.get(id).equals(winScore)) {
                winId = id;
                break;
            }
        }
        mRG.saveResults(results);
        mWSS.notifyGameOver(winId);
        mGameIdUser.clear();
        mGameIdScore.clear();
        mGameField.clearField();
        mStatus = STATUS_WAIT;
    }


    @Override
    public byte getStatus() {
        return mStatus;
    }

    @Override
    public int generateGameId() {
        return mLastId.getAndIncrement();
    }

    @Override
    public boolean hasPlaces() {
        return mFreePlaces.get() > 0;
    }

    @Override
    public void onPackmansMoved(int gamerId, String coordinates) {
        mWSS.notifyMovement(gamerId, coordinates);
    }

    @Override
    public void onPointEated(int gamerId) {
        ScoreDAO.Score gamerScore = mGameIdScore.get(gamerId);
        gamerScore.setScore(gamerScore.getScore() + POINT_SCORE);
    }

    @Override
    public void onPackmanEated(int whoId) {
        ScoreDAO.Score gamerScore = mGameIdScore.get(whoId);
        gamerScore.setScore(gamerScore.getScore() + PACKMAN_SCORE);
    }

}
