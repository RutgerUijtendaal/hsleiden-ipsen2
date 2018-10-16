package daos;

import models.Child;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChildDao implements GenericDao<Child>{
    private final String tableName = "child";
    private final String[] columnNames= {
            "couple_id",
            "is_born",
            "date"
    };

    @Override
    public List<Child> getAll() {
        return DaoManager.getAll(this);
    }

    @Override
    public Child getById(int id) {
        return DaoManager.getById(this, id);
    }

    @Override
    public int save(Child savedChild) {
        return DaoManager.save(this, savedChild);
    }

    @Override
    public boolean update(Child updatedChild) {
        return DaoManager.update(this, updatedChild, updatedChild.getId());
    }

    @Override
    public boolean delete(Child deletedChild) {
        return DaoManager.delete(this, deletedChild.getId());
    }

    @Override
    public boolean deleteById(int childId) {
        return DaoManager.delete(this, childId);
    }

    @Override
    public Child createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            int couple_id = resultSet.getInt(columnNames[0]);
            boolean is_born = resultSet.getBoolean(columnNames[1]);
            Date date = resultSet.getDate(columnNames[2]);

            return new Child(id, couple_id, date, is_born);
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Child child){
        try {
            preparedStatement.setInt(1, child.getCouple_id());
            preparedStatement.setBoolean(2, child.getIsBorn());
            preparedStatement.setDate(3, child.getDate());
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String[] getColumnNames() {
        return columnNames;
    }
}

