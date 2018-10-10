package daos;

import models.Child;

import java.util.List;

public class ChildDao implements GenericDao<Child>{
    @Override
    public List<Child> getAll() {
        return null;
    }

    @Override
    public Child getByPK(int pk) {
        return null;
    }

    @Override
    public void save(Child savedChild) {

    }

    @Override
    public void update(Child updatedChild, String[] params) {

    }

    @Override
    public void delete(Child deletedChild) {

    }
}

