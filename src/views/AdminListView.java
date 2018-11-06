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

public class AdminListView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button backBtn;

    private @FXML Button noticeYesBtn;

    private @FXML Button addAdminBtn;

    private @FXML TextField email;

    private @FXML ListView<Admin> resultsList;

    private AdminListController adminListController;

    private boolean isAdmin = false;

    double smallChange = 1.05;
    double bigChange = 1.1;

    private FilteredList<Admin> filteredList;
    private Admin selectedAdmin;

    public AdminListView(AdminListController adminListController) {
        this.adminListController = adminListController;
        rootFXML = super.loadFXML("../fxml/admin_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

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

    public void setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
        setupAdminView();
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleConfirmDelete() {
        adminListController.deleteAdmin(selectedAdmin);
    }

    public void handleBackBtnClick() {
        adminListController.handleBackBtnClick();
    }

    public void handleAddAdminBtnClick() {
        adminListController.handleAddAdminBtnClick();}

    public void clearListData() {
        resultsList.getItems().clear();
    }

    @Override
    public void hideNotice() {
        doFadeOut(noticePane);
        resultsList.setMouseTransparent(false);
    }

    public void switchToSingleNotice() {
        noticeBtn.setTranslateX(0);
        noticeBtn.setText("OK");
        noticeYesBtn.setVisible(false);
    }

    public void switchToDoubleNotice() {
        noticeBtn.setTranslateX(60);
        noticeBtn.setText("Nee");
        noticeYesBtn.setVisible(true);
    }

    public HBox makeRow(Admin admin) {

        int imgSize = 50;

        String email = admin.getEmail();

        Region spacer = new Region();
        HBox mainBox = new HBox();
        HBox deleteBox = new HBox();
        HBox emailBox = new HBox();
        emailBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Image deleteImg = new Image(this.getClass().getResourceAsStream("../resources/delete.png"));
        ImageView deleteImgView = new ImageView(deleteImg);
        deleteImgView.setFitHeight(imgSize);
        deleteImgView.setFitWidth(imgSize);
        super.setScaleTransitions(deleteImgView, bigChange);

        HBox imageBox = new HBox();
        Image editImg = new Image(this.getClass().getResourceAsStream("../resources/edit.png"));
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
            adminListController.editAdmin(admin);
        });

        return mainBox;
    }

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

    public void addAdmins(List<Admin> dilemmas) {
        filteredList = new FilteredList<>(FXCollections.observableArrayList(dilemmas), e->true);
        resultsList.setItems(filteredList);
    }

    public void deleteRow(Admin admin) {
        ObservableList<Admin> list = FXCollections.observableArrayList(filteredList);
        list.remove(admin);
        filteredList = new FilteredList<>(list, e->true);
        resultsList.setItems(filteredList);
    }
}

