package ru.bagrusss.servces.database;


public class UserStatistics {
    private long userId;
    private long winGamesCount;
    private long gamesCount;

    public long getGamesCount() {
        return gamesCount;
    }

    public void setGamesCount(long gamesCount) {
        this.gamesCount = gamesCount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getWinGamesCount() {
        return winGamesCount;
    }

    public void setWinGamesCount(long winGamesCount) {
        this.winGamesCount = winGamesCount;
    }
}
