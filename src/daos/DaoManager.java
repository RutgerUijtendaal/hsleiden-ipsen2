package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoManager {
    private static AdminDao adminDao;
    private static CoupleDao coupleDao;
    private static ChildDao childDao;
    private static ParentDao parentDao;
    private static DilemmaDao dilemmaDao;
    private static AnswerDao answerDao;
    private static ResultDao resultDao;

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

    public static PreparedStatement getPreparedStatement(String statement){
        Connection connection = ConnectionFactory.getConnection();
        try {
            return connection.prepareStatement(statement);
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
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
}

