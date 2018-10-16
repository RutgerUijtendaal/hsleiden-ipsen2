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
        List<Child> children = new ArrayList<>();

        PreparedStatement preparedStatement = DaoManager.getSelectAllStatement(tableName);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                children.add(createChildFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return children;
    }

    @Override
    public Child getById(int id) {
        Child child = null;

        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            child = createChildFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return child;
    }

    @Override
    public int save(Child savedChild) {
        int generatedKey = -1;

        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);

        try{
            fillPreparedStatement(statement, savedChild);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            generatedKey = resultSet.getInt(1);statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return generatedKey;
    }

    @Override
    public boolean update(Child updatedChild) {
        boolean successfull = false;

        PreparedStatement statement = DaoManager.getUpdateStatement(columnNames, tableName, updatedChild.getId());

        try{
            fillPreparedStatement(statement, updatedChild);
            successfull = statement.executeUpdate() == 1;
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return successfull;
    }

    @Override
    public boolean delete(Child deletedChild) {
        boolean successfull = false;

        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedChild.getId());

        try{
            successfull = statement.executeUpdate() == 1;
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return successfull;
    }

    private Child createChildFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int couple_id = resultSet.getInt(columnNames[0]);
        boolean is_born = resultSet.getBoolean(columnNames[1]);
        Date date = resultSet.getDate(columnNames[2]);

        return new Child(id,couple_id,date,is_born);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Child child) throws SQLException {
        preparedStatement.setInt(1, child.getCouple_id());
        preparedStatement.setBoolean(2, child.getIsBorn());
        preparedStatement.setDate(3, child.getDate());
    }
}

