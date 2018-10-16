package controllers;

import javafx.application.Platform;
import javafx.stage.Stage;
import service.MailService;
import views.BaseView;

import models.Dilemma;

import javax.mail.MessagingException;

public class AppController {

    private Stage appStage;

    private MainMenuController mmc;
    private AddCoupleController acc;
    private AddDilemmaController adc;
    private AdminMenuController amc;
    private LoginMenuController lmc;
    private CoupleListController clc;
    private DilemmaListController dlc;
    private MailService mailService;
    private BaseView activeView;

    public AppController(Stage appStage) {
        this.appStage = appStage;
        switchToMainMenuView();
    }

    private void switchView(BaseView view) {
        activeView = view;
        appStage.setScene(view.getScene());
    }

    public void switchToMainMenuView() {
        if (mmc == null) {
            mmc = new MainMenuController(this);
        }
        switchView(mmc.getView());
    }

    public void switchToAddCoupleView() {
        if (acc == null) {
            acc = new AddCoupleController(this);
        }
        switchView(acc.getView());
    }

    public void switchToAdminMenuView() {
        if (amc == null) {
            amc = new AdminMenuController(this);
        }
        switchView(amc.getView());
    }

    public void switchToLoginView() {
        if (lmc == null) {
            lmc = new LoginMenuController(this);
        }
        switchView(lmc.getView());
    }

    public void switchToCoupleListView() {
        if (clc == null) {
            clc = new CoupleListController(this);
        }
        switchView(clc.getView());
    }

    public void switchToAnswerDilemmaView(String email) {
    }

    public void switchToDilemmaListView() {
        if (dlc == null) {
            dlc = new DilemmaListController(this);
        }
        switchView(dlc.getView());
    }

    public void switchToAddDilemmaView() {
        if (adc == null) {
            adc = new AddDilemmaController(this);
        }
        switchView(adc.getView());
    }

    public void switchToEditDilemmaView(Dilemma dilemma) {
        if (adc == null) {
            adc = new AddDilemmaController(this);
        }
        switchView(adc.getView());
        adc.fillFields(dilemma);

    }

    public void sendMail(String to, String subject, String content) {
        if (mailService == null) {
            mailService = new MailService("dubiogroep9", "dreamteam_en_bas");
        }
        try {
            mailService.send(to, subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
            activeView.displayError("Something went wrong!");
        }
    }

    public void shutdown() {
        //Shutdown javafx thread
        Platform.exit();
        //Make sure application is closed
        System.exit(0);
    }
}
