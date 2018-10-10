package controllers;

import javafx.stage.Stage;
import service.MailService;

import javax.mail.MessagingException;

public class AppController {

    private Stage appStage;

    private MainMenuController mmc;
    private AddCoupleController acc;
    private AdminMenuController amc;
    private LoginMenuController lmc;
    private MailService mailService;

    public AppController(Stage appStage) {
        this.appStage = appStage;
        switchToMainMenuView();
    }

    public void switchToMainMenuView() {
        if (mmc == null) {
            mmc = new MainMenuController(this);
        }
        appStage.setScene(mmc.getViewScene());
    }

    public void switchToAddCoupleView() {
        if (acc == null) {
            acc = new AddCoupleController(this);
        }
        appStage.setScene(acc.getViewScene());
    }

    public void switchToAdminMenuView() {
        if (amc == null) {
            amc = new AdminMenuController(this);
        }
        appStage.setScene(amc.getViewScene());
    }

    public void switchToLoginView() {
        if (lmc == null) {
            lmc = new LoginMenuController(this);
        }
        appStage.setScene(lmc.getViewScene());
    }

    public void switchToAnswerDilemmaView(String email) {
    }

    public void switchToDilemmaListView() {
    }

    public void switchToAddDilemmaView() {
    }

    public void switchToEditDilemmaView() {
    }

    public void sendMail(String to, String subject, String content) {
        if (mailService == null) {
            mailService = new MailService("dubiogroep9", "dreamteam_en_bas");
        }
        try {
            mailService.send(to, subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
            //TODO show popup
        }
    }

}
