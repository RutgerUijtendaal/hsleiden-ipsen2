package daos;

import models.Right;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RightDao implements GenericDao<Right> {
    private final String tableName = "rights";
    private final String[] columnNames= {
            "can_edit_dilemma",
            "can_view_statistics"
    };

    @Override
    public List<Right> getAll() {
        return GenericDaoImplementation.getAll(this);
    }

    @Override
    public Right getById(int id) {
        return GenericDaoImplementation.getById(this, id);
    }

    @Override
    public int save(Right savedRight) {
        return GenericDaoImplementation.save(this, savedRight);
    }

    @Override
    public boolean update(Right updatedRight) {
        return GenericDaoImplementation.update(this, updatedRight, updatedRight.getId());
    }

    @Override
    public boolean delete(Right deletedRight) {
        return GenericDaoImplementation.delete(this, deletedRight.getId());
    }

    @Override
    public boolean deleteById(int coupleId) {
        return GenericDaoImplementation.delete(this, coupleId);
    }

    @Override
    public Right createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            boolean can_edit_dilemma = resultSet.getBoolean(columnNames[0]);
            boolean can_view_statistics = resultSet.getBoolean(columnNames[1]);

            return new Right(id, can_edit_dilemma, can_view_statistics);
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Right right){
        try {
            preparedStatement.setBoolean(1, right.getCan_edit_dilemma());
            preparedStatement.setBoolean(2, right.getCan_view_statistics());
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
