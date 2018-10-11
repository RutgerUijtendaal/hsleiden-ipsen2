package daos;

import models.Parent;

import java.util.List;

public class ParentDao implements GenericDao<Parent>{
    @Override
    public List<Parent> getAll() {
        return null;
    }

    @Override
    public Parent getByPK(int pk) {
        return null;
    }

    @Override
    public void save(Parent savedParent) {

    }

    @Override
    public void update(Parent updatedParent, String[] params) {

    }

    @Override
    public void delete(Parent deletedParent) {

    }
}

