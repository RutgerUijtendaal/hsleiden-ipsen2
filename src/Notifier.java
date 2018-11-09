import daos.ParentDao;
import daos.ResultDao;
import models.Parent;
import models.Result;
import service.MailService;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;

@SuppressWarnings("ConfusingArgumentToVarargsMethod")
public class Notifier {
    private static final MailService MAIL_SERVICE = new MailService("dubiogroep9", "dreamteam_en_bas");

    private static final ParentDao PARENT_DAO = new ParentDao();
    private static final ResultDao RESULT_DAO = new ResultDao();

    private static final String MAIL_TEMPLATE = "Best {0}, <br />\n" +
            "<br />\n" +
            "Er staat een nieuw dilemma voor u klaar. Klikt op de onderstaande link om deze te beantwoorden.<br />\n" +
            "<a href=\"{1}\">{1}</a><br />\n" +
            "<br />\n" +
            "\n" +
            "<strong>Is uw kind geboren? klik dan om op onderstaande link:</strong><br />\n" +
            "<a href=\"{2}\">{2}</a><br />\n" +
            "<br />\n" +
            "\n" +
            "Het dubio team.<br />\n" +
            "<img src=\"http://www.garage2020.nl/wp-content/uploads/2018/04/Logo-Dubio_schaduw-04-04.png\" width=\"300\" />";


    public static void main(String[] args) {
        Thread mailThread = new Thread(() -> {
            try {
                List<Parent> parents = PARENT_DAO.getAll();

                for (Parent parent: parents) {
                    String mail = MessageFormat.format(MAIL_TEMPLATE, new String[] {parent.getFirstName(), "https://beantwoorddilemma.nl", "https://kindisgeboren.nl"});
                    System.out.println(mail);
                    MAIL_SERVICE.threadedSend(parent.getEmail(), "Er staat een dilemma voor u klaar", mail);

                    makeNewResultRecord(parent);

                    Thread.sleep(500);
                }

            } catch (InterruptedException | MessagingException exception) {
                exception.printStackTrace();
            }
        }, "mailthread");

        mailThread.start();
    }

    private static void makeNewResultRecord(Parent parent){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Result result = new Result(parent.getId(), null, timestamp, null);
        RESULT_DAO.save(result);
    }
}
