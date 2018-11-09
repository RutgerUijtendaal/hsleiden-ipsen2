package daos;

/**
 * keeps instances of the DAOs so they can be staticly
 * accessed in the application
 *
 * @author Bas de Bruyn
 */
public class DaoManager {
    private static final AdminDao adminDao = new AdminDao();
    private static final CoupleDao coupleDao = new CoupleDao();
    private static final ChildDao childDao = new ChildDao();
    private static final ParentDao parentDao = new ParentDao();
    private static final DilemmaDao dilemmaDao = new DilemmaDao();
    private static final AnswerDao answerDao = new AnswerDao();
    private static final RightDao rightDao = new RightDao();
    private static final CoupleListDao coupleListDao = new CoupleListDao();
    private static final ResultDao resultDao = new ResultDao();

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

