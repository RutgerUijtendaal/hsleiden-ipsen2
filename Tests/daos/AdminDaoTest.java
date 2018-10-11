package daos;

import models.Admin;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminDaoTest {

    @org.junit.jupiter.api.Test
    void getAll() {
        List<Admin> admins = DaoManager.getAdminDao().getAll();
        for(Admin admin : admins){
            System.out.println(admin.getId());
        }
    }
}