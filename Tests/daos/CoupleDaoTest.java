package daos;

import models.Couple;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

class CoupleDaoTest {

    @Test
    void getAll() {
        List<Couple> couples = DaoManager.getCoupleDao().getAll();
        for(Couple couple : couples){
            System.out.println(couple.getSignupDate());
        }
    }

    @Test
    void delete() {
        DaoManager.getCoupleDao().delete(new Couple(1,Date.valueOf("2018-10-11"),1,2));
    }
}