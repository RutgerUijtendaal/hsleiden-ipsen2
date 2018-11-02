package daos;

import models.Right;
import org.junit.jupiter.api.Test;

import java.util.List;

class RightDaoTest {

    @Test
    void getAll() {
        List<Right> rights = DaoManager.getRightDao().getAll();
        for (Right right : rights){
            System.out.println(right.toString());
        }
    }

    @Test
    void getById() {
        Right right = DaoManager.getRightDao().getById(7);
        System.out.println(right.toString());
    }

    @Test
    void save() {
        DaoManager.getRightDao().save(new Right(true, true, true));
    }

    @Test
    void update() {
        DaoManager.getRightDao().update(new Right(7,false, false, false));
    }

    @Test
    void delete() {
        DaoManager.getRightDao().delete(new Right(7,false, false, false));
    }
}