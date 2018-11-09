package daos;

import exceptions.*;
import models.DatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * super class of all generic DAOs
 * a generic DAO represents a table from the database
 *
 * all standard CRUD opeations are defined here
 *
 * this class also provides commonly used methods for executing and closing statements
 *
 * to get information about it's subclasses,
 * abstract methods are defined so they can be called from their
 * super class (this class)
 *
 * @author Bas de Bruyn
 * @param <T> class of the model the dao interacts with
 */
public abstract class GenericDao<T>{

    private GenericDao<T> daoSubclass;

    public GenericDao() {
        daoSubclass = getDao();
    }

    public List<T> getAll() {
        PreparedStatement preparedStatement = PreparedStatementFactory.createSelectAllStatement(daoSubclass.getTableName());

        return executeGetAll(preparedStatement);
    }

    public T getById(int id) {
        PreparedStatement statement = PreparedStatementFactory.createSelectByIdStatement(daoSubclass.getTableName(), id);

        return executeGetByAttribute(statement);
    }

    /**
     * @return the id the database automaticly generated for the object
     */
    public int save(T savedObject) {
        int generatedKey;
        PreparedStatement statement = PreparedStatementFactory.createInsertStatement(daoSubclass.getTableName(), daoSubclass.getColumnNames());

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

    /**
     * @return if the object was successfully updated
     */
    @SuppressWarnings("unchecked")
    public boolean update(DatabaseObject<T> updatedObject) {
        PreparedStatement statement = PreparedStatementFactory.createUpdateStatement(daoSubclass.getColumnNames(), daoSubclass.getTableName(), updatedObject.getId());

        daoSubclass.fillPreparedStatement(statement, (T)updatedObject);

        boolean successfull = executeUpdate(statement);

        closeTransaction(statement);

        return successfull;
    }

    /**
     * @return if the object was successfully deleted
     */
    public boolean deleteById(int deletedObjectId) {
        PreparedStatement statement = PreparedStatementFactory.createDeleteStatement(daoSubclass.getTableName(), deletedObjectId);

        boolean successfull = executeUpdate(statement);

        closeTransaction(statement);

        return successfull;
    }

    /**
     * @return if the object was successfully deleted
     */
    public boolean delete(DatabaseObject<T> deletedObject) {
        return deleteById(deletedObject.getId());
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

    /**
     * executes a statement that returns if a condition is true
     */
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

    /**
     * executes a statement that returns an object
     * that has a certain value for an attribute
     */
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

    public static void closeTransaction(PreparedStatement statement){
        try{
            Connection connection = statement.getConnection();
            statement.close();
            connection.close();
        } catch (SQLException exception){
            throw new CloseDatabaseConnectionException();
        }
    }

    public static void fillParamater(PreparedStatement statement, int index, String value){
        try {
            statement.setString(index, value);
        } catch (SQLException exception) {
            throw new FillPreparedStatementException();
        }
    }

    public static void fillParamater(PreparedStatement statement, int index, int value){
        try {
            statement.setInt(index, value);
        } catch (SQLException exception) {
            throw new FillPreparedStatementException();
        }
    }

    public static void fillParamater(PreparedStatement statement, int index, short value){
        try {
            statement.setShort(index, value);
        } catch (SQLException exception) {
            throw new FillPreparedStatementException();
        }
    }
    
    public abstract T createFromResultSet(ResultSet resultSet);

    public abstract void fillPreparedStatement(PreparedStatement preparedStatement, T object);

    public abstract String getTableName();

    public abstract String[] getColumnNames();

    public abstract GenericDao<T> getDao();
}
