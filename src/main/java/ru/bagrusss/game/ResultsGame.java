package ru.bagrusss.game;

import ru.bagrusss.helpers.BaseInterface;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;

import java.util.List;

/**
 * Created by vladislav
 */
@SuppressWarnings("unused")
public interface ResultsGame extends BaseInterface {

    boolean saveResults(List<ScoreDAO.Score> results);

    ScoreDataSet getInfo(long userId);
}
