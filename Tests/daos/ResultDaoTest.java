package daos;

import models.Result;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class ResultDaoTest {

    @Test
    void getAll() {
    }

    @Test
    void getById() {
        Result result = DaoManager.getResultDao().getById(1);
        System.out.println(result);
    }

    @Test
    void save() {
        int generatedKey = DaoManager.getResultDao().save(new Result(10, null, new Timestamp(System.currentTimeMillis()),null));
        System.out.println(generatedKey);
    }

    @Test
    void update() {
        Result result = DaoManager.getResultDao().getById(1);
        result.setAnswer_id(36);
        result.setAnsweredTime(new Timestamp(System.currentTimeMillis()));
        boolean successfull = DaoManager.getResultDao().update(result);
        System.out.println(successfull);
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}