package daos;

import models.CoupleListModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoupleListDaoTest {

    @Test
    void getAll() {
        List<CoupleListModel> coupleListModels = DaoManager.getCoupleListDao().getAll();
        for (CoupleListModel coupleListModel : coupleListModels){
            System.out.println(coupleListModel);
        }
    }

    @Test
    void getById() {
        CoupleListModel coupleListModel = DaoManager.getCoupleListDao().getById(14);
        System.out.println(coupleListModel);
    }

    @Test
    void getByEmail() {
        CoupleListModel coupleListModel = DaoManager.getCoupleListDao().getByEmail("filleremail3@gmail.com");
        System.out.println(coupleListModel);
    }
}