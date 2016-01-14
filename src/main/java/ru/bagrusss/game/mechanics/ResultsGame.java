package ru.bagrusss.game.mechanics;

import ru.bagrusss.helpers.BaseInterface;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;

import java.util.List;

/**
 * Created by vladislav
 */
@SuppressWarnings("unused")
public interface ResultsGame extends BaseInterface {

    void saveResults(List<ScoreDAO.Score> results);

    ScoreDataSet getInfo(long userId);

    List<ScoreDataSet> getBestGamers(long count);

}
