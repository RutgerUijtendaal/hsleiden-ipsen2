package daos;

import models.Admin;

import java.util.List;

public class AdminDao implements GenericDao<Admin>{
    @Override
    public List<Admin> getAll() {
        return null;
    }

    @Override
    public Admin getByPK(int pk) {
        return null;
    }

    @Override
    public void save(Admin newAdmin) {

    }

    @Override
    public void update(Admin updatedAdmin, String[] params) {

    }

    @Override
    public void delete(Admin deletedAdmin) {

    }
}

