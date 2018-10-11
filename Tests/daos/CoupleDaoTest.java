package daos;

import models.Admin;
import models.Couple;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoupleDaoTest {

    @Test
    void getAll() {
        List<Couple> couples = DaoManager.getCoupleDao().getAll();
        for(Couple couple : couples){
            System.out.println(couple.getSignupDate());
        }
    }
}