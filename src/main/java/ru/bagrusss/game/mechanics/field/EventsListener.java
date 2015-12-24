package ru.bagrusss.game.mechanics.field;

/**
 * нужен комнате для работы со счетом игроков
 */

@SuppressWarnings("unused")

public interface EventsListener {

    void onPackmansMoved(byte gamerId, String coordinates);

    void onBonusGenerated(byte bonusId);

    void onPointGenerated();

}
