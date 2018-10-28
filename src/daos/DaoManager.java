package daos;

public class DaoManager {
    private static AdminDao adminDao;
    private static CoupleDao coupleDao;
    private static ChildDao childDao;
    private static ParentDao parentDao;
    private static DilemmaDao dilemmaDao;
    private static AnswerDao answerDao;
    private static RightDao rightDao;
    private static CoupleListDao coupleListDao;
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

    public static ResultDao getResultDao(){
        if(resultDao == null){
            resultDao = new ResultDao();
        }
        return resultDao;
    }

}

