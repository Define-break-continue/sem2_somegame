package ru.bagrusss.game.mechanics.field;

import java.util.List;

/**
 * нужен комнате для работы со счетом игроков
 */
@SuppressWarnings("unused")

public interface EventsListener {

    void onPackmanEated(byte gamerId);

    void onPackmansMoved(byte gamerId, List<Integer> coordinates);

    void onPointsEated(byte gamerId, byte count);

    void onBonusActivated(byte gamerId, byte bonusId);

}
