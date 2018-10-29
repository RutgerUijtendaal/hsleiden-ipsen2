package daos;

public class DaoManager {
    private static AdminDao adminDao = new AdminDao();
    private static CoupleDao coupleDao = new CoupleDao();
    private static ChildDao childDao = new ChildDao();
    private static ParentDao parentDao = new ParentDao();
    private static DilemmaDao dilemmaDao = new DilemmaDao();
    private static AnswerDao answerDao = new AnswerDao();
    private static RightDao rightDao = new RightDao();
    private static CoupleListDao coupleListDao = new CoupleListDao();
    private static ResultDao resultDao = new ResultDao();

    public static AdminDao getAdminDao() {
        return adminDao;
    }

    public static CoupleDao getCoupleDao() {
        return coupleDao;
    }

    public static ChildDao getChildDao() {
        return childDao;
    }

    public static ParentDao getParentDao() {
        return parentDao;
    }

    public static DilemmaDao getDilemmaDao() {
        return dilemmaDao;
    }

    public static AnswerDao getAnswerDao() {
        return answerDao;
    }

    public static RightDao getRightDao(){
        return rightDao;
    }

    public static CoupleListDao getCoupleListDao(){
        return coupleListDao;
    }

    public static ResultDao getResultDao(){
        return resultDao;
    }

}

