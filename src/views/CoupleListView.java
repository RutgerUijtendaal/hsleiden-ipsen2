package views;

import controllers.CoupleListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.CoupleListModel;

import java.util.List;

/**
 * View to show all the couples
 *
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 */
public class CoupleListView extends BaseView {

    private @FXML
    final Parent rootFXML;

    private @FXML Button backBtn;

    private @FXML Button noticeYesBtn;

    private @FXML TextField email;

    private @FXML ListView<CoupleListModel> resultsList;

    private final CoupleListController coupleListController;

    private boolean isAdmin = false;

    private FilteredList<CoupleListModel> filteredList;
    private CoupleListModel seletectedCoupleListModel;

    public CoupleListView(CoupleListController coupleListController) {
        this.coupleListController = coupleListController;
        rootFXML = super.loadFXML("/fxml/parent_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(noticeYesBtn, smallChange);
        super.setScaleTransitions(email, smallChange);

        email.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(coupleListModel ->{
            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            if(coupleListModel.getParent1().getEmail().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }else if(coupleListModel.getParent2().getEmail().toLowerCase().contains(lowerCaseFilter)){
                return true;
            } else if (coupleListModel.getParent1().getPhoneNr().contains(lowerCaseFilter)) {
                return true;
            } else return coupleListModel.getParent2().getPhoneNr().contains(lowerCaseFilter);
        }));

        resultsList.setCellFactory(lv -> createListCell());
    }

    public void setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleConfirmDelete() {
        coupleListController.deleteCouple(seletectedCoupleListModel);
    }

    public void handleBackBtnClick() {
        coupleListController.handleBackBtnClick();
    }

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

    private void switchToDoubleNotice() {
        noticeBtn.setTranslateX(60);
        noticeBtn.setText("Nee");
        noticeYesBtn.setVisible(true);
    }

    private HBox makeRow(CoupleListModel couple) {

        int imgSize = 50;

        String email1 = couple.getParent1().getEmail();
        String phoneNr1 = couple.getParent1().getPhoneNr();
        String email2 = couple.getParent2().getEmail();
        String phoneNr2 = couple.getParent2().getPhoneNr();

        Region spacer = new Region();
        HBox mainBox = new HBox();
        HBox deleteBox = new HBox();
        VBox phoneNrBox = new VBox();
        VBox emailBox = new VBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        emailBox.setSpacing(1);
        phoneNrBox.setSpacing(1);

        mainBox.getChildren().addAll(emailBox, spacer, phoneNrBox, deleteBox);
        Image deleteImg = new Image(this.getClass().getResourceAsStream("/resources/delete.png"));
        ImageView deleteImgView = new ImageView(deleteImg);
        deleteImgView.setFitHeight(imgSize);
        deleteImgView.setFitWidth(imgSize);
        phoneNrBox.setPadding(new Insets(0,10,0,10));
        double bigChange = 1.1;
        super.setScaleTransitions(deleteImgView, bigChange);

        emailBox.getChildren().addAll(new Label(email1), new Label(email2));
        phoneNrBox.getChildren().addAll(new Label(phoneNr1), new Label(phoneNr2));
        deleteBox.getChildren().add(deleteImgView);
        deleteBox.setAlignment(Pos.CENTER_RIGHT);

        //If admin rights aren't set hide delete option
        if(!isAdmin) {
            deleteImgView.setVisible(false);
        }

        deleteImgView.setOnMouseClicked( (MouseEvent e ) -> {
            switchToDoubleNotice();
            super.displayPopup("Ouderpaar permanent verwijderen?");
            resultsList.setMouseTransparent(true);
            seletectedCoupleListModel = couple;
        });

        return mainBox;
    }

    private ListCell<CoupleListModel> createListCell() {
        return new ListCell<CoupleListModel>() {
            @Override
            protected void updateItem(CoupleListModel coupleListModel, boolean empty) {
                super.updateItem(coupleListModel, empty);
                if (empty || coupleListModel == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(makeRow(coupleListModel));
                }
            }
        };
    }

    public void addCouples(List<CoupleListModel> dilemmas) {
        filteredList = new FilteredList<>(FXCollections.observableArrayList(dilemmas), e->true);
        resultsList.setItems(filteredList);
    }

    public void deleteRow(CoupleListModel coupleListModel) {
        ObservableList<CoupleListModel> list = FXCollections.observableArrayList(filteredList);
        list.remove(coupleListModel);
        filteredList = new FilteredList<>(list, e->true);
        resultsList.setItems(filteredList);
    }
}

