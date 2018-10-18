package daos;

import exceptions.FailedToCloseConnectionException;
import exceptions.FailedToReadFromResultSetException;
import exceptions.FailedToExecutePreparedStatementException;
import models.DatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<T>{

    private GenericDao<T> daoSubclass;

    public GenericDao() {
        daoSubclass = getDao();
    }

    public  List<T> getAll() {
        List<T> answers = new ArrayList<>();

        PreparedStatement preparedStatement = PreparedStatementFactory.getSelectAllStatement(daoSubclass.getTableName());

        ResultSet resultSet = executeQuery(preparedStatement);

        try{
            while (resultSet.next()) {
                answers.add(daoSubclass.createFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        } finally {
            closeTransaction(preparedStatement);
        }

        return answers;
    }

    public  T getById(int id) {
        T object;

        PreparedStatement statement = PreparedStatementFactory.getSelectByIdStatement(daoSubclass.getTableName(), id);

        ResultSet resultSet = executeQuery(statement);

        try {
            resultSet.next();
            object = daoSubclass.createFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            closeTransaction(statement);
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        } finally {
            closeTransaction(statement);
        }

        return object;
    }

    public  int save(T savedObject) {
        int generatedKey;
        PreparedStatement statement = PreparedStatementFactory.getInsertStatement(daoSubclass.getTableName(), daoSubclass.getColumnNames());

        daoSubclass.fillPreparedStatement(statement, savedObject);
        execute(statement);

        try{
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            generatedKey = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        } finally {
            closeTransaction(statement);
        }

        return generatedKey;
    }

    public  boolean update(DatabaseObject<T> updatedObject) {
        PreparedStatement statement = PreparedStatementFactory.getUpdateStatement(daoSubclass.getColumnNames(), daoSubclass.getTableName(), updatedObject.getId());

        daoSubclass.fillPreparedStatement(statement, (T)updatedObject);

        boolean successfull = executeUpdate(statement);

        closeTransaction(statement);

        return successfull;
    }

    public boolean deleteById(int deletedObjectId) {
        PreparedStatement statement = PreparedStatementFactory.getDeleteStatement(daoSubclass.getTableName(), deletedObjectId);

        boolean successfull = executeUpdate(statement);

        closeTransaction(statement);

        return successfull;
    }

    public boolean delete(DatabaseObject<T> deletedObject) {
        return deleteById(deletedObject.getId());
    }

    public static void closeTransaction(PreparedStatement statement){
       try{
           Connection connection = statement.getConnection();
           statement.close();
           connection.close();
       } catch (SQLException exception){
           exception.printStackTrace();
           throw new FailedToCloseConnectionException();
       }
    }

    public static ResultSet executeQuery(PreparedStatement preparedStatement){
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToExecutePreparedStatementException();
        }
    }

    public static void execute(PreparedStatement preparedStatement){
        try {
            preparedStatement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToExecutePreparedStatementException();
        }
    }

    public static boolean executeUpdate(PreparedStatement preparedStatement){
        try {
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToExecutePreparedStatementException();
        }
    }
    
    public abstract T createFromResultSet(ResultSet resultSet);

    public abstract void fillPreparedStatement(PreparedStatement preparedStatement, T object);

    public abstract String getTableName();

    public abstract String[] getColumnNames();

    public abstract GenericDao<T> getDao();
}
