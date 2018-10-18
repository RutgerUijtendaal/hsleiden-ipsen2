package daos;

import models.Admin;
import models.DatabaseObject;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

class AdminDaoTest {

    @Test
    void getAll() {
        List<Admin> admins = DaoManager.getAdminDao().getAll();
        for(Admin admin : admins){
            System.out.println(admin.getId());
        }
    }


    @Test
    void getById() {
        Admin admin = DaoManager.getAdminDao().getById(9);
    }

    @Test
    void save() {
        int generatedKey = DaoManager.getAdminDao().save(new Admin("mail4@admin.com","asdasdas",3, new Date(System.currentTimeMillis())));
        System.out.println(generatedKey);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}