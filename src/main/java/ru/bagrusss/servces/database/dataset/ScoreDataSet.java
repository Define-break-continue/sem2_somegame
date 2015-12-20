package ru.bagrusss.servces.database.dataset;

/**
 * Created by vladislav on 19.12.15.
 */
@SuppressWarnings("unused")
public class ScoreDataSet {

    private long userId;
    private long games;
    private long wins;
    private long lose;

    public ScoreDataSet(long id, long games, long wins, long lose) {
        this.userId = id;
        this.games = games;
        this.wins = wins;
        this.lose = lose;
    }

    public long getId() {
        return userId;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public long getGames() {
        return games;
    }

    public void setGames(long games) {
        this.games = games;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getLose() {
        return lose;
    }

    public void setLose(long lose) {
        this.lose = lose;
    }
}
