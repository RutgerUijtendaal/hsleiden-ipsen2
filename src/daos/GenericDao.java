package daos;

import exceptions.CloseDatabaseConnectionException;
import exceptions.ExecutePreparedStatementException;
import exceptions.ReadFromResultSetException;
import exceptions.NoFurtherResultsException;
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
        PreparedStatement preparedStatement = PreparedStatementFactory.getSelectAllStatement(daoSubclass.getTableName());

        return executeGetAll(preparedStatement);
    }

    public  T getById(int id) {
        PreparedStatement statement = PreparedStatementFactory.getSelectByIdStatement(daoSubclass.getTableName(), id);

        return executeGetByAttribute(statement);
    }

    public int save(T savedObject) {
        int generatedKey;
        PreparedStatement statement = PreparedStatementFactory.getInsertStatement(daoSubclass.getTableName(), daoSubclass.getColumnNames());

        daoSubclass.fillPreparedStatement(statement, savedObject);
        execute(statement);

        try{
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                generatedKey = resultSet.getInt(1);
                resultSet.close();
            } else {
                throw new NoFurtherResultsException();
            }
        } catch (SQLException exception){
            throw new ReadFromResultSetException();
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
           throw new CloseDatabaseConnectionException();
       }
    }

    public static ResultSet executeQuery(PreparedStatement preparedStatement){
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException exception){
            throw new ExecutePreparedStatementException();
        }
    }

    public static void execute(PreparedStatement preparedStatement){
        try {
            preparedStatement.execute();
        } catch (SQLException exception){
            throw new ExecutePreparedStatementException();
        }
    }

    public static boolean executeUpdate(PreparedStatement preparedStatement){
        try {
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException exception){
            throw new ExecutePreparedStatementException();
        }
    }

    public static boolean executeIsTrue(PreparedStatement statement){
        boolean isTrue;

        ResultSet resultSet = executeQuery(statement);

        try {
            if(resultSet.next()) {
                isTrue = resultSet.getBoolean(1);
            } else {
                throw new NoFurtherResultsException();
            }
            resultSet.close();
        } catch (SQLException exception) {
            throw new ReadFromResultSetException();
        } finally {
            closeTransaction(statement);
        }

        return isTrue;
    }

    public T executeGetByAttribute(PreparedStatement statement){
        T object;

        ResultSet resultSet = executeQuery(statement);

        try {
            if(resultSet.next()) {
                object = daoSubclass.createFromResultSet(resultSet);
            } else {
                object = null;
            }
            resultSet.close();
        } catch (SQLException exception) {
            throw new ReadFromResultSetException();
        } finally {
            closeTransaction(statement);
        }

        return object;
    }

    public List<T> executeGetAll(PreparedStatement statement){
        List<T> objects = new ArrayList<>();

        ResultSet resultSet = executeQuery(statement);

        try{
            while (resultSet.next()) {
                objects.add(daoSubclass.createFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            throw new ReadFromResultSetException();
        } finally {
            closeTransaction(statement);
        }

        return objects;
    }
    
    public abstract T createFromResultSet(ResultSet resultSet);

    public abstract void fillPreparedStatement(PreparedStatement preparedStatement, T object);

    public abstract String getTableName();

    public abstract String[] getColumnNames();

    public abstract GenericDao<T> getDao();
}
