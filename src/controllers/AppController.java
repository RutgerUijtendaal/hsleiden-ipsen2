package controllers;

import javafx.application.Platform;
import javafx.stage.Stage;
import models.Admin;
import models.Right;
import service.MailService;
import views.BaseView;

import models.Dilemma;

import javax.mail.MessagingException;

public class AppController {

    private Stage appStage;

    private MainMenuController mmc;
    private AddCoupleController acc;
    private EditDilemmaController edc;
    private AddDilemmaController adc;
    private AdminLoginController alc;
    private AdminMenuController amc;
    private AddAdminController aac;
    private LoginMenuController lmc;
    private CoupleListController clc;
    private DilemmaListController dlc;
    private MailService mailService;
    private BaseView activeView;
    private StatisticController statisticController;

    private Admin admin;
    private Right rights;

    public BaseView getActiveView() {
        return activeView;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Right getRights() {
        return rights;
    }

    public void setRights(Right rights) {
        this.rights = rights;
    }

    private void clearLogin() {
        admin = null;
        rights = null;
        // Reset controllers that depend on login
        clc = null;
        dlc = null;
        amc = null;
    }

    public AppController(Stage appStage) {
        this.appStage = appStage;
        switchToMainMenuView();
    }

    private void switchView(BaseView view) {
        activeView = view;
        appStage.setScene(view.getScene());
    }

    public void switchToMainMenuView() {
        // When user returns to main screen reset login data.
        clearLogin();

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
        clc.loadCouples();
    }

    public void switchToAnswerDilemmaView(String email) {
    }

    public void switchToAdminLoginView() {
        alc = new AdminLoginController(this);
        switchView(alc.getView());
    }

    public void switchToDilemmaListView() {
        if (dlc == null) {
            dlc = new DilemmaListController(this);
        }
        switchView(dlc.getView());
        dlc.loadDilemmas();
    }

    public void switchToAddDilemmaView() {
        if (edc == null && adc == null) {
            adc = new AddDilemmaController(this);
            adc.createView();
        } else if (edc != null && adc == null) {
            adc = new AddDilemmaController(this);
            edc.getView().setController(adc);
            adc.setView(edc.getView());
        } else if (edc == null && adc != null) {
            // do nothing
            // this means the user went in and out of adddilemmaview
        } else if (edc != null && adc != null) {
            edc.getView().setController(adc);
        }
        adc.clearFields();
        switchView(adc.getView());
    }

    public void switchToEditDilemmaView(Dilemma dilemma) {
        if (edc == null && adc == null) {
            edc = new EditDilemmaController(this);
            edc.createView();
        } else if (edc != null && adc == null) {
            // do nothing
            // this means the user went in and out of editdilemmaview
        } else if (edc == null && adc != null) {
            edc = new EditDilemmaController(this);
            adc.getView().setController(edc);
            edc.setView(adc.getView());
        } else if (edc != null && adc != null) {
            adc.getView().setController(edc);
        }
        edc.clearFields();
        edc.fillFields(dilemma);
        switchView(edc.getView());
    }

    public void switchToAddAdminView() {
        aac = new AddAdminController(this);
        switchView(aac.getView());
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

    public void switchToStatisticsView() {
        if (statisticController == null) {
            statisticController = new StatisticController(this);
        }
        switchView(statisticController.getView());
    }
}
