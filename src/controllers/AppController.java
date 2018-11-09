package controllers;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;
import service.MailService;
import util.AdminSubmitData;
import views.BaseView;

import javax.mail.MessagingException;

/**
 * The main class that handles the switching of views
 * This class also holds all the controllers that are
 * used in the application
 * 
 * Beyond those two functions this class also is the
 * class called to do basic mail sending and has the
 * capability to show popups to the current view.
 *
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 * @author Rutger Uijtendaal
 * @author Danny van Tol
 * @author Bas de Bruyn
 */

public class AppController {

    private final Stage appStage;

    private final MainMenuController mainMenuController;
    private AddCoupleController addCoupleController;
    private EditDilemmaController editDilemmaController;
    private AddDilemmaController addDilemmaController;
    private AddAdminController addAdminController;
    private EditAdminController editAdminController;
    private AdminMenuController adminMenuController;
    private AdminLoginController adminLoginController;
    private LoginMenuController loginMenuController;
    private CoupleListController coupleListController;
    private DilemmaListController dilemmaListController;
    private AdminListController adminListController;
    private StatisticController statisticController;
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
        Runnable runnable = () -> {
            coupleListController = new CoupleListController(this);
            dilemmaListController = new DilemmaListController(this);
            adminMenuController = new AdminMenuController(this);
            System.out.println("login cleared");
        };
        new Thread(runnable).start();
    }

    private void loadControllers() {
        Runnable runnable = () -> {
            loginMenuController = new LoginMenuController(this);
            addCoupleController = new AddCoupleController(this);
            addAdminController = new AddAdminController(this);
            addAdminController.createView();
            editAdminController = new EditAdminController(this);
            editAdminController.setView(addAdminController.getView());
            adminMenuController = new AdminMenuController(this);
            adminLoginController = new AdminLoginController(this);
            coupleListController = new CoupleListController(this);
            dilemmaListController = new DilemmaListController(this);
            editDilemmaController = new EditDilemmaController(this);
            addDilemmaController = new AddDilemmaController(this);
            adminListController = new AdminListController(this);
            statisticController = new StatisticController(this);
            editDilemmaController.createView();
            addDilemmaController.setView(editDilemmaController.getView());
            mailService = new MailService("dubiogroep9", "dreamteam_en_bas");
        };
        new Thread(runnable).start();
    }

    public AppController(Stage appStage) {
        mainMenuController = new MainMenuController(this);
        this.appStage = appStage;
        switchToMainMenuView();
        loadControllers();
    }

    /**
     * Does the fade effect from view to view
     *
     * @param view is the new view that has to be switched in
     * @see views.BaseView#getFillPane()
     */
    private void doViewFade(BaseView view) {
        BaseView tempView = activeView;
        activeView = view;

        FadeTransition ft = new FadeTransition(Duration.millis(100), tempView.getFillPane());
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> {

        appStage.setScene(view.getScene());

        FadeTransition ft2 = new FadeTransition(Duration.millis(100), view.getFillPane());
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

    public void switchToAdminListView() {
        adminListController.setRights(rights);
        switchView(adminListController.getView());
        adminListController.loadAdmins();
    }

    public void switchToCoupleListView() {
        coupleListController.setRights(rights);
        switchView(coupleListController.getView());
        coupleListController.loadCouples();
    }

    public void switchToAnswerDilemmaView(Parent parent, Couple couple, Child child) {
        AnswerDilemmaController answerDilemmaController = new AnswerDilemmaController(this, parent, couple, child);
        switchView(answerDilemmaController.getView());
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
        addAdminController.setView(editAdminController.getView());
        addAdminController.getView().setController(addAdminController);
        switchView(addAdminController.getView());
    }

    public void switchToEditAdminView(AdminSubmitData asd) {
        editAdminController = new EditAdminController(this);
        editAdminController.setView(addAdminController.getView());
        editAdminController.getView().setController(editAdminController);
        editAdminController.fillFields(asd);
        switchView(addAdminController.getView());
    }

    public void switchToStatisticsView() {
        switchView(statisticController.getView());
    }

    /**
     * Takes three arguments in order to send a mail
     * using the MailService class
     *
     * This is done here so that every controller
     * is able to send mail since every controller
     * has access to the AppController
     *
     * @param to email-adress to which the email should be send
     * @param subject subjects of the mail
     * @param content contents of the mail
     * @see service.MailService#threadedSend(String, String, String)
     */
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
