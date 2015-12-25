package ru.bagrusss.servces.database.dataset;

/**
 * Created by vladislav on 19.12.15.
 */
@SuppressWarnings("unused")
public class ScoreDataSet {

    private long userId;
    private long games;
    private long wins;

    private long score;

    public String getEmail() {
        return email;
    }

    private String email;

    public ScoreDataSet(long id, long games, long wins, long score) {
        this.userId = id;
        this.games = games;
        this.wins = wins;
        this.score = score;
    }

    public ScoreDataSet setEmail(String email) {
        this.email = email;
        return this;
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

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreDataSet scoreDataSet = (ScoreDataSet) o;
        return games == scoreDataSet.games && wins == scoreDataSet.wins && score == scoreDataSet.score;
    }

    @Override
    public int hashCode() {
        int result = (int) (games ^ (games >>> 32));
        result = 31 * result + (int) (wins ^ (wins >>> 32));
        result = 31 * result + (int) (score ^ (score >>> 32));
        return result;
    }
}
