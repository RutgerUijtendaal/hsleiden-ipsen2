package daos;

import exceptions.FailedToCloseConnectionException;
import exceptions.FailedToReadFromResultSetException;
import exceptions.FailedToExecutePreparedStatementException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenericDaoImplementation{


    public static <T> List<T> getAll(GenericDao<T> dao) {
        List<T> answers = new ArrayList<>();

        PreparedStatement preparedStatement = PreparedStatementFactory.getSelectAllStatement(dao.getTableName());

        ResultSet resultSet = executeQuery(preparedStatement);

        try{
            while (resultSet.next()) {
                answers.add((dao.createFromResultSet(resultSet)));
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

    public static <T> T getById(GenericDao<T> dao, int id) {
        T object;

        PreparedStatement statement = PreparedStatementFactory.getSelectByIdStatement(dao.getTableName(), id);

        ResultSet resultSet = executeQuery(statement);

        try {
            resultSet.next();
            object = dao.createFromResultSet(resultSet);
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

    public static <T> int save(GenericDao<T> dao, T savedObject) {
        int generatedKey;
        PreparedStatement statement = PreparedStatementFactory.getInsertStatement(dao.getTableName(), dao.getColumnNames());

        dao.fillPreparedStatement(statement, savedObject);
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

    public static <T> boolean update(GenericDao<T> dao, T updatedObject, int objectId) {
        PreparedStatement statement = PreparedStatementFactory.getUpdateStatement(dao.getColumnNames(), dao.getTableName(), objectId);

        dao.fillPreparedStatement(statement, updatedObject);

        boolean successfull = executeUpdate(statement);

        closeTransaction(statement);

        return successfull;
    }

    public static <T> boolean delete(GenericDao<T> dao, int deletedObjectId) {
        PreparedStatement statement = PreparedStatementFactory.getDeleteStatement(dao.getTableName(), deletedObjectId);

        boolean successfull = executeUpdate(statement);

        closeTransaction(statement);

        return successfull;
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
            preparedStatement.executeQuery();
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
}
