package daos;

import models.DatabaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoManager {
    private static AdminDao adminDao;
    private static CoupleDao coupleDao;
    private static ChildDao childDao;
    private static ParentDao parentDao;
    private static DilemmaDao dilemmaDao;
    private static AnswerDao answerDao;
    private static RightDao rightDao;
    private static CoupleListDao coupleListDao;

    public static AdminDao getAdminDao() {
        if(adminDao == null){
            adminDao = new AdminDao();
        }
        return adminDao;
    }

    public static CoupleDao getCoupleDao() {
        if(coupleDao == null){
            coupleDao = new CoupleDao();
        }
        return coupleDao;
    }

    public static ChildDao getChildDao() {
        if(childDao == null){
            childDao = new ChildDao();
        }
        return childDao;
    }

    public static ParentDao getParentDao() {
        if(parentDao == null){
            parentDao = new ParentDao();
        }
        return parentDao;
    }

    public static DilemmaDao getDilemmaDao() {
        if(dilemmaDao == null){
            dilemmaDao = new DilemmaDao();
        }
        return dilemmaDao;
    }

    public static AnswerDao getAnswerDao() {
        if(answerDao == null){
            answerDao = new AnswerDao();
        }
        return answerDao;
    }

    public static RightDao getRightDao(){
        if(rightDao == null){
            rightDao = new RightDao();
        }
        return rightDao;
    }

    public static CoupleListDao getCoupleListDao(){
        if(coupleListDao == null){
            coupleListDao = new CoupleListDao();
        }
        return coupleListDao;
    }

     public static void closeTransaction(PreparedStatement statement){
        try{
            Connection connection = statement.getConnection();
            statement.close();
            connection.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
     }

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

        DaoManager.closeTransaction(preparedStatement);

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

        DaoManager.closeTransaction(statement);

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

        DaoManager.closeTransaction(statement);

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

        DaoManager.closeTransaction(statement);

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

        DaoManager.closeTransaction(statement);

        return successfull;
    }
}

