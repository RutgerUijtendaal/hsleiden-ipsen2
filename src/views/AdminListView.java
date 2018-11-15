package views;

import controllers.AdminListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import models.Admin;

import java.util.List;

/**
 * View to display all the admins
 *
 * @author Rutger Uijtendaal
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 */
public class AdminListView extends BaseView {

    private @FXML
    final Parent rootFXML;

    private @FXML Button backBtn;

    private @FXML Button noticeYesBtn;

    private @FXML Button addAdminBtn;

    private @FXML TextField email;

    private @FXML ListView<Admin> resultsList;

    private final AdminListController adminListController;

    private boolean isAdmin = false;

    private FilteredList<Admin> filteredList;
    private Admin selectedAdmin;

    public AdminListView(AdminListController adminListController) {
        this.adminListController = adminListController;
        rootFXML = super.loadFXML("/fxml/admin_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(noticeYesBtn, smallChange);
        super.setScaleTransitions(email, smallChange);

        email.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(admin ->{
            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            return admin.getEmail().toLowerCase().contains(lowerCaseFilter);
        }));

        resultsList.setCellFactory(lv -> createListCell());

    }

    /**
     * Displays the extra buttons for admins
     * @param admin
     */
    public void setIsAdmin(boolean admin) {
        this.isAdmin = admin;
        setupAdminView();
    }

    /**
     * Handles button from fxml file
     */
    public void handleConfirmDelete() {
        adminListController.deleteAdmin(selectedAdmin);
    }

    /**
     * Handles button from fxml file
     */
    public void handleBackBtnClick() {
        adminListController.handleBackBtnClick();
    }

    /**
     * Handles button from fxml file
     */
    public void handleAddAdminBtnClick() {
        adminListController.handleAddAdminBtnClick();}

    public void clearListData() {
        resultsList.getItems().clear();
    }

    /**
     * Handles button from fxml file
     */
    @Override
    public void hideNotice() {
        doFadeOut(noticePane);
        resultsList.setMouseTransparent(false);
    }

    /**
     * Handles button from fxml file
     */
    public void switchToSingleNotice() {
        noticeBtn.setTranslateX(0);
        noticeBtn.setText("OK");
        noticeYesBtn.setVisible(false);
    }
    /**
     * Handles button from fxml file
     */
    private void switchToDoubleNotice() {
        noticeBtn.setTranslateX(60);
        noticeBtn.setText("Nee");
        noticeYesBtn.setVisible(true);
    }

    /**
     * Makes one row for the list
     * @param admin The model needed to build a row
     * @return a row
     */
    private HBox makeRow(Admin admin) {

        int imgSize = 50;

        String email = admin.getEmail();

        Region spacer = new Region();
        HBox mainBox = new HBox();
        HBox deleteBox = new HBox();
        HBox emailBox = new HBox();
        emailBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Image deleteImg = new Image(this.getClass().getResourceAsStream("/resources/delete.png"));
        ImageView deleteImgView = new ImageView(deleteImg);
        deleteImgView.setFitHeight(imgSize);
        deleteImgView.setFitWidth(imgSize);
        double bigChange = 1.1;
        super.setScaleTransitions(deleteImgView, bigChange);

        HBox imageBox = new HBox();
        Image editImg = new Image(this.getClass().getResourceAsStream("/resources/edit.png"));
        ImageView editImgView = new ImageView(editImg);
        editImgView.setFitHeight(imgSize);
        editImgView.setFitWidth(imgSize);
        editImgView.setPickOnBounds(false);

        imageBox.getChildren().add(editImgView);
        mainBox.getChildren().addAll(emailBox, spacer, imageBox, deleteBox);

        super.setScaleTransitions(imageBox, bigChange);

        emailBox.getChildren().addAll(new Label(email));
        deleteBox.getChildren().add(deleteImgView);
        deleteBox.setAlignment(Pos.CENTER_RIGHT);

        //If admin rights aren't set hide delete option
        if(!isAdmin) {
            imageBox.setVisible(false);
            deleteImgView.setVisible(false);
        }

        deleteImgView.setOnMouseClicked(e -> {
            switchToDoubleNotice();
            super.displayPopup("Beheerder permanent verwijderen?");
            resultsList.setMouseTransparent(true);
            selectedAdmin = admin;
        });

        editImgView.setOnMouseClicked(e -> {
            selectedAdmin = admin;
            switchToSingleNotice();
            adminListController.editAdmin(admin);
        });

        return mainBox;
    }

    /**
     * Sets the graphic to a cell in the listview
     * @return A cell with the proper styling
     */
    private ListCell<Admin> createListCell() {
        return new ListCell<Admin>() {
            @Override
            protected void updateItem(Admin admin, boolean empty) {
                super.updateItem(admin, empty);
                if (empty || admin == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(makeRow(admin));
                }
            }
        };
    }

    private void setupAdminView() {
        if(isAdmin) {
            addAdminBtn.setVisible(true);
            email.setMaxWidth(400);
            email.setTranslateX(0.0);
        }
    }

    /**
     * Adds rows to the list
     * @param admins list of admins to be added to the list
     */
    public void addAdmins(List<Admin> admins) {
        filteredList = new FilteredList<>(FXCollections.observableArrayList(admins), e->true);
        resultsList.setItems(filteredList);
    }

    /**
     * Deletes a row from the list
     * @param admin row that needs to be deleted
     */
    public void deleteRow(Admin admin) {
        ObservableList<Admin> list = FXCollections.observableArrayList(filteredList);
        list.remove(admin);
        filteredList = new FilteredList<>(list, e->true);
        resultsList.setItems(filteredList);
    }
}

