package controllers;

import javafx.application.Platform;
import javafx.stage.Stage;
import models.Child;
import models.Couple;
import models.Parent;
import models.Admin;
import models.Right;
import service.MailService;
import views.BaseView;

import models.Dilemma;

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
        coupleListController = null;
        dilemmaListController = null;
        adminMenuController = null;
    }

    private void loadControllers() {
        Runnable runnable = () -> {
            rights = new Right(true, true);
            mainMenuController = new MainMenuController(this);
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
        };
        Thread controllerThread = new Thread(runnable);
        controllerThread.start();
    }

    public AppController(Stage appStage) {
        loadControllers();
        this.appStage = appStage;
        switchToMainMenuView();
    }

    private void switchView(BaseView view) {
        activeView = view;
        appStage.setScene(view.getScene());
    }

    public void switchToMainMenuView() {
        clearLogin();

        if (mainMenuController == null) {
            mainMenuController = new MainMenuController(this);
        }
        switchView(mainMenuController.getView());
    }

    public void switchToAddCoupleView() {
        if (addCoupleController == null) {
            addCoupleController = new AddCoupleController(this);
        }
        switchView(addCoupleController.getView());
    }

    public void switchToAdminMenuView() {
        if (adminMenuController == null) {
            adminMenuController = new AdminMenuController(this);
        }
        switchView(adminMenuController.getView());
    }

    public void switchToLoginView() {
        if (loginMenuController == null) {
            loginMenuController = new LoginMenuController(this);
        }
        switchView(loginMenuController.getView());
    }

    public void switchToCoupleListView() {
        if (coupleListController == null) {
            coupleListController = new CoupleListController(this);
        }
        switchView(coupleListController.getView());
        coupleListController.loadCouples();
    }

    public void switchToAnswerDilemmaView(Parent parent, Couple couple, Child child) {
        if (answerDilemmaController == null)
            answerDilemmaController = new AnswerDilemmaController(this, parent, couple, child);

        appStage.setScene(answerDilemmaController.getViewScene());
    }

    public void switchToAdminLoginView() {
        adminLoginController = new AdminLoginController(this);
        switchView(adminLoginController.getView());
    }

    public void switchToDilemmaListView() {
        if (dilemmaListController == null) {
            dilemmaListController = new DilemmaListController(this);
        }
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
