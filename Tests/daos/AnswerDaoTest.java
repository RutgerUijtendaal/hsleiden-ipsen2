package daos;

import models.Answer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerDaoTest {

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void getByDilemmaId() {
        Answer[] answers = DaoManager.getAnswerDao().getByDilemmaId(12);
        System.out.println(answers[0]);
        System.out.println(answers[1]);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void createFromResultSet() {
    }

    @Test
    void fillPreparedStatement() {
    }

    @Test
    void getTableName() {
    }

    @Test
    void getColumnNames() {
    }
}