package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoManager {
    private static AdminDao adminDao;
    private static CoupleDao coupleDao;
    private static ChildDao childDao;
    private static ParentDao parentDao;
    private static DilemmaDao dilemmaDao;
    private static AnswerDao answerDao;
    private static ResultDao resultDao;
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

    public static ResultDao getResultDao(){
        if(resultDao == null){
            resultDao = new ResultDao();
        }
        return resultDao;
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

    public static PreparedStatement getPreparedStatement(String query){
        Connection connection = ConnectionFactory.getConnection();
        try {
            connection.setAutoCommit(true);
            return connection.prepareStatement(query);
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static PreparedStatement getPreparedStatementWithReturn(String query) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static void rollBackTransaction(PreparedStatement statement) {
        try {
            Connection connection = statement.getConnection();
            connection.rollback();
        } catch (SQLException exception ) {
            exception.printStackTrace();
        }
    }


    public static void closeTransaction(PreparedStatement statement){
        try{
            Connection connection = statement.getConnection();
            statement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
        }
     }

     public static PreparedStatement getSelectAllStatement(String table){
        String query = "SELECT * FROM " + table + ";";
        PreparedStatement statement = getPreparedStatement(query);
        return statement;
     }

    public static PreparedStatement getSelectByIdStatement(String table, int id){
        String query = "SELECT * FROM " + table + " WHERE id = " + id + ";";
        PreparedStatement statement = getPreparedStatement(query);
        return statement;
    }

    public static PreparedStatement getInsertStatement(String table, String[] columnNames){
        String query = "INSERT INTO " + table + "(" + columnNames[0];
        for (int i = 1; i < columnNames.length; i++) {
            query += "," + columnNames[i];
        }
        query += ")" +
                " VALUES(?";
        for (int i = 1; i < columnNames.length; i++) {
            query += ",?";
        }
        query += ");";

        return getPreparedStatement(query);
    }

    public static PreparedStatement getInsertStatementWithReturn(String table, String[] columnNames){
        String query = "INSERT INTO " + table + "(" + columnNames[0];
        for (int i = 1; i < columnNames.length; i++) {
            query += "," + columnNames[i];
        }
        query += ")" +
                " VALUES(?";
        for (int i = 1; i < columnNames.length; i++) {
            query += ",?";
        }
        query += ");";

        return getPreparedStatementWithReturn(query);
    }

    public static PreparedStatement getUpdateStatement(String[] columnNames, String table, int id){
        String query = "UPDATE " + table;
        query += " SET " + columnNames[0] + " = ?";
        for (int i = 1; i < columnNames.length; i++) {
            query += " , " + columnNames[i] + " = ?";
        }
        query += " WHERE id = " + id + ";";

        return getPreparedStatement(query);
    }

    public static PreparedStatement getDeleteStatement(String table, int id){
        String query = "DELETE FROM " + table + " WHERE id = " + id + ";";
        PreparedStatement statement = getPreparedStatement(query);
        return statement;
    }
}

