package daos;

import models.Answer;
import models.Dilemma;

import java.util.List;

public class AnswerDao implements GenericDao<Answer>{
    @Override
    public List<Answer> getAll() {
        return null;
    }

    @Override
    public Answer getByPK(int pk) {
        return null;
    }

    public Answer[] getByDilemma(Dilemma dilemma) {
        return null;
    }

    @Override
    public void save(Answer savedAnswer) {

    }

    @Override
    public void update(Answer updatedAnswer, String[] params) {

    }

    @Override
    public void delete(Answer deletedAnswer) {

    }
}

