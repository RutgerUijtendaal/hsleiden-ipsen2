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
    public void save(Child savedChild) {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);

        try{
            fillPreparedStatement(statement, savedChild);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    public void saveNew(Child savedChild) throws SQLException {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName,columnNames);
        try{
            fillPreparedStatement(statement, savedChild);
            statement.execute();
        } catch (SQLException exception){
            DaoManager.rollBackTransaction(statement);
            throw exception;
        }
        DaoManager.closeTransaction(statement);
    }

    @Override
    public void update(Child updatedChild) {
        PreparedStatement statement = DaoManager.getUpdateStatement(columnNames, tableName, updatedChild.getId());

        try{
            fillPreparedStatement(statement, updatedChild);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public void delete(Child deletedChild) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedChild.getId());

        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
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

