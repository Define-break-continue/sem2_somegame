package ru.bagrusss.helpers;

/**
 * Created by vladislav
 */

@SuppressWarnings("ALL")
public class ServerConfigs {
    private int port;
    private String frontend;
    private int rooms;
    private int gamers;
    private int units;
    private long gametime;

    public int getPort() {
        return port;
    }

    public String getFrontendPath() {
        return frontend;
    }

    public int getRooms() {
        return rooms;
    }

    public int getGamers() {
        return gamers;
    }

    public int getUnits() {
        return units;
    }

    public long getGametime() {
        return gametime;
    }
}
