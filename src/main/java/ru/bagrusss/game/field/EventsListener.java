package ru.bagrusss.game.field;

/**
 * нужен комнате для работы со счетом игроков
 */

@SuppressWarnings("unused")

public interface EventsListener {

    void onPackmansMoved(int gamerId, String coordinates);

    /**
     * Вызывается, когда пакмен съел точку для увеличения очков
     *
     * @param gamerId - чей
     */
    void onPointEated(int gamerId);

    /**
     * @param whoId - кому поднять счет
     */

    void onPackmanEated(int whoId);

}
