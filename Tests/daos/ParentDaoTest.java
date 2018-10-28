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

    @Test
    void getByPK() {
    }

    @Test
    void save() {
        DaoManager.getParentDao().save(new Parent("06-12345678","testNaam","test@test.nl"));
    }

    @Test
    void update() {
        DaoManager.getParentDao().update(new Parent(20,"+31600000019","testNaam","filleremail20@gmail.com"));
    }

    @Test
    void delete() {
        DaoManager.getParentDao().delete(new Parent(21,"06-12345678","testNaam","test@test.nl"));
    }

    @Test
    void getById() {
    }

    @Test
    void checkIfEmailsExists() {
        boolean exists = DaoManager.getParentDao().emailExists("filleremail100@gmail.com");
        System.out.println(exists);
    }
}