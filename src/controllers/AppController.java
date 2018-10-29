package controllers;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;
import service.MailService;
import views.BaseView;

import javax.mail.MessagingException;

public class AppController {

    private Stage appStage;

    private MainMenuController mainMenuController;
    private AddCoupleController addCoupleController;
    private EditDilemmaController editDilemmaController;
    private AddDilemmaController addDilemmaController;
    private AddAdminController addAdminController;
    private AdminMenuController adminMenuController;
    private AdminLoginController adminLoginController;
    private LoginMenuController loginMenuController;
    private AnswerDilemmaController answerDilemmaController;
    private CoupleListController coupleListController;
    private DilemmaListController dilemmaListController;
    private MailService mailService;
    private BaseView activeView;

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
        coupleListController = new CoupleListController(this);
        dilemmaListController = new DilemmaListController(this);
        adminMenuController = new AdminMenuController(this);
    }

    private void loadControllers() {
        Runnable runnable = () -> {
            loginMenuController = new LoginMenuController(this);
            addCoupleController = new AddCoupleController(this);
            addAdminController = new AddAdminController(this);
            adminMenuController = new AdminMenuController(this);
            adminLoginController = new AdminLoginController(this);
            coupleListController = new CoupleListController(this);
            dilemmaListController = new DilemmaListController(this);
            editDilemmaController = new EditDilemmaController(this);
            addDilemmaController = new AddDilemmaController(this);
            editDilemmaController.createView();
            addDilemmaController.setView(editDilemmaController.getView());
            mailService = new MailService("dubiogroep9", "dreamteam_en_bas");
        };
        Thread controllerThread = new Thread(runnable);
        controllerThread.start();
    }

    public AppController(Stage appStage) {
        mainMenuController = new MainMenuController(this);
        this.appStage = appStage;
        switchToMainMenuView();
        loadControllers();
    }

    private void doViewFade(BaseView view) {

                FadeTransition ft = new FadeTransition(Duration.millis(200), activeView.getFillPane());
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setOnFinished(e -> {

                    activeView = view;
                    appStage.setScene(view.getScene());

                    FadeTransition ft2 = new FadeTransition(Duration.millis(200), view.getFillPane());
                    ft2.setFromValue(1);
                    ft2.setToValue(0);
                    ft2.play();

                });
                ft.play();
    }

    private void switchView(BaseView view) {

        if (activeView != null) {
            doViewFade(view);
        } else {
            activeView = view;
            appStage.setScene(view.getScene());
        }
    }

    public void switchToMainMenuView() {
        clearLogin();
        switchView(mainMenuController.getView());
    }

    public void switchToAddCoupleView() {
        switchView(addCoupleController.getView());
    }

    public void switchToAdminMenuView() {
        adminMenuController.setRights(rights);
        switchView(adminMenuController.getView());
    }

    public void switchToLoginView() {
        switchView(loginMenuController.getView());
    }

    public void switchToCoupleListView() {
        coupleListController.setRights(rights);
        switchView(coupleListController.getView());
        coupleListController.loadCouples();
    }

    public void switchToAnswerDilemmaView(Parent parent, Couple couple, Child child) {
        answerDilemmaController = new AnswerDilemmaController(this, parent, couple, child);
        appStage.setScene(answerDilemmaController.getViewScene());
    }

    public void switchToAdminLoginView() {
        adminLoginController = new AdminLoginController(this);
        switchView(adminLoginController.getView());
    }

    public void switchToDilemmaListView() {
        dilemmaListController.setRights(rights);
        switchView(dilemmaListController.getView());
        dilemmaListController.loadDilemmas();
    }

    public void switchToAddDilemmaView() {
        editDilemmaController.getView().setController(addDilemmaController);
        addDilemmaController.clearFields();
        switchView(addDilemmaController.getView());
    }

    public void switchToEditDilemmaView(Dilemma dilemma) {
        addDilemmaController.getView().setController(editDilemmaController);
        editDilemmaController.clearFields();
        editDilemmaController.fillFields(dilemma);
        switchView(editDilemmaController.getView());
    }

    public void switchToAddAdminView() {
        addAdminController = new AddAdminController(this);
        switchView(addAdminController.getView());
    }

    public void sendMail(String to, String subject, String content) {
        try {
            mailService.threadedSend(to, subject, content);
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
