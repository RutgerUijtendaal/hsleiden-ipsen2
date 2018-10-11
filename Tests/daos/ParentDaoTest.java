package daos;

import models.Admin;
import models.Parent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParentDaoTest {

    @Test
    void getAll() {
        List<Parent> parents = DaoManager.getParentDao().getAll();
        for(Parent parent : parents){
            System.out.println(parent.getId());
        }
    }
}