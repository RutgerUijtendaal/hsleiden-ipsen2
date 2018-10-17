package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenericDaoImplementation{


    public static <T> List<T> getAll(GenericDao<T> dao) {
        List<T> answers = new ArrayList<>();

        PreparedStatement preparedStatement = PreparedStatementFactory.getSelectAllStatement(dao.getTableName());

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                answers.add((dao.createFromResultSet(resultSet)));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        PreparedStatementFactory.closeTransaction(preparedStatement);

        return answers;
    }

    public static <T> T getById(GenericDao<T> dao, int id) {
        T object = null;

        PreparedStatement statement = PreparedStatementFactory.getSelectByIdStatement(dao.getTableName(), id);

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            object = dao.createFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        PreparedStatementFactory.closeTransaction(statement);

        return object;
    }

    public static <T> int save(GenericDao<T> dao, T savedObject) {
        int generatedKey = -1;
        PreparedStatement statement = PreparedStatementFactory.getInsertStatement(dao.getTableName(), dao.getColumnNames());

        try{
            dao.fillPreparedStatement(statement, savedObject);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            generatedKey = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        PreparedStatementFactory.closeTransaction(statement);

        return generatedKey;
    }

    public static <T> boolean update(GenericDao<T> dao, T updatedObject, int objectId) {
        boolean successfull = false;

        PreparedStatement statement = PreparedStatementFactory.getUpdateStatement(dao.getColumnNames(), dao.getTableName(), objectId);

        try{
            dao.fillPreparedStatement(statement, updatedObject);
            successfull = statement.executeUpdate() == 1;
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        PreparedStatementFactory.closeTransaction(statement);

        return successfull;
    }

    public static <T> boolean delete(GenericDao<T> dao, int deletedObjectId) {
        boolean successfull = false;

        PreparedStatement statement = PreparedStatementFactory.getDeleteStatement(dao.getTableName(), deletedObjectId);

        try{
            successfull = statement.executeUpdate() == 1;
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        PreparedStatementFactory.closeTransaction(statement);

        return successfull;
    }
}
