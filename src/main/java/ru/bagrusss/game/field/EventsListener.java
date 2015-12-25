package ru.bagrusss.game.field;

/**
 * нужен комнате для работы со счетом игроков
 */

@SuppressWarnings("unused")

public interface EventsListener {

    void onPackmansMoved(byte gamerId, String coordinates);

    /**
     * Вызывается, когда пакмен съел точку для увеличения очков
     *
     * @param gamerId - чей
     */
    void onPointEated(byte gamerId);

    /**
     * @param whoId - кому поднять счет
     */

    void onPackmanEated(byte whoId);

    /**
     * Сообщить фротну, что появился бонус на поле
     *
     * @param bonusId - какой бонус
     * @param point   - где разместить
     */
    void onBonusGenerated(byte bonusId, GameField.Point point);

    /**
     * Сообщить фронту, что появилась тока
     *
     * @param coordinate - где появилась точка
     */
    void onPointGenerated(GameField.Point coordinate);

    /**
     * Сообщить фронту, что съеден бонус нового пакмена и добавить его на поле
     *
     * @param gamerId    - кому добавить пакмена
     * @param coordinate - координата
     */
    void onPackmanGenerated(byte gamerId, GameField.Point coordinate);


}
