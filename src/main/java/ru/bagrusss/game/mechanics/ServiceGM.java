package ru.bagrusss.game.mechanics;

import com.google.gson.JsonObject;
import ru.bagrusss.apiservlets.websocket.WebSocketService;
import ru.bagrusss.game.field.GameField;
import ru.bagrusss.helpers.Resources;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vladislav
 */
@SuppressWarnings({"unused"})
public class ServiceGM implements GameMechanicsService {

    public static final int PACKMAN_SCORE = 10;
    public static final int POINT_SCORE = 1;
    private final ConcurrentHashMap<Integer, UserDataSet> mGameIdUser;
    private final ConcurrentHashMap<Integer, ScoreDAO.Score> mGameIdScore;
    private final WebSocketService mWSS = (WebSocketService) Main.getAppContext()
            .get(WebSocketService.class);
    private final ResultsGame mRG = (ResultsGame) Main.getAppContext()
            .get(ResultsGame.class);
    private final AtomicInteger mFreePlaces = new AtomicInteger(5);
    private final AtomicInteger mLastId = new AtomicInteger(1);
    private byte mMaxWalls;
    private short mMaxPoints;
    private byte mMaxPacmans;
    private byte mLength;
    private byte mHeight;
    private byte lastX;
    private byte lastY;
    private GameField mGameField;

    public ServiceGM() {
        mGameIdUser = new ConcurrentHashMap<>();
        mGameIdScore = new ConcurrentHashMap<>();
    }

    public void confugure(String path) throws IOException {
        JsonObject conf = Resources.readResourses(path, JsonObject.class);
        try {
            mLength = conf.get("length").getAsByte();
            mHeight = conf.get("height").getAsByte();
            mMaxPoints = conf.get("points").getAsShort();
            mMaxPacmans = conf.get("gamerPackmans").getAsByte();
            mMaxWalls = conf.get("walls").getAsByte();
        } catch (NullPointerException e) {
            System.exit(Main.CONFIGS_ERROR);
        }
        mGameField = new GameField(mLength, mHeight, this);
        mGameField.setMaxWalls(mMaxWalls);
        mGameField.setMaxPoints(mMaxPoints);
        mGameField.setMaxPacmansForGamer(mMaxPacmans);
        mGameField.prepareFieldToGame(new ArrayList<>(mGameIdUser.keySet()));
    }

    @Override
    public boolean joinToGame(UserDataSet user, int gamerId) {
        if (mFreePlaces.get() - 1 == 0) {
            return false;
        }
        mGameIdUser.put(gamerId, user);
        ScoreDAO.Score score = new ScoreDAO.Score(gamerId, false, 0);
        mGameIdScore.put(gamerId, score);
        return true;
    }

    @Override
    public void leaveGame(int gamerId) {
        mGameIdUser.remove(gamerId);
        mGameIdScore.remove(gamerId);
        mFreePlaces.incrementAndGet();
    }

    @Override
    public void moveGamerUnits(int gamerId, byte dir) {
        mGameField.moveUnits(gamerId, dir);
    }

    void startGame() {
        mWSS.notifyStartGame();
    }

    void gameOver() {
        List<ScoreDAO.Score> results = new ArrayList<>(mGameIdScore.values());
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
        mWSS.notifyFinishGame(winId);
        mGameIdUser.clear();
        mGameIdScore.clear();
    }

    @Override
    public byte getStatus() {
        return 0;
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
