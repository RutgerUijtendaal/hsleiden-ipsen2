package views;



import controllers.DilemmaListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import models.Dilemma;

public class DilemmaListView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button backBtn;
    private @FXML Button noticeYesBtn;

    private @FXML TextField dilemmaSearch;

    private @FXML ListView<Dilemma> resultsList;
    private FilteredList<Dilemma> filteredList;

    private DilemmaListController dlc;

    private Dilemma selectedDilemma;

    private Boolean isAdmin = false;

    double smallChange = 1.05;
    double bigChange = 1.1;

    public DilemmaListView(DilemmaListController dlc) {
        this.dlc = dlc;
        rootFXML = super.loadFXML("../fxml/dilemma_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        super.setScaleTransitions(backBtn, smallChange);

        super.setScaleTransitions(dilemmaSearch, smallChange);

        dilemmaSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(dilemma ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(dilemma.getTheme().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(Integer.toString(dilemma.getWeekNr()).contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });

        resultsList.setCellFactory(lv -> createListCell());
    }

    public void setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    public void handleBackBtnClick() {
        dlc.handleBackBtnClick();
    }

    public void clearListData() {
        resultsList.getItems().clear();
    }

    @Override
    public void hideNotice() {
        doFadeOut(noticePane);
        resultsList.setMouseTransparent(false);
    }

    public HBox makeRow(Dilemma dilemma) {

        int imgSize = 50;

        String dilemmaStr = dilemma.getTheme();
        short dilemmaWeek = dilemma.getWeekNr();
        int id = dilemma.getId();

        Region spacer = new Region();
        HBox mainBox = new HBox();
        HBox rightBox = new HBox();
        VBox leftBox = new VBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        leftBox.setSpacing(1);

        mainBox.getChildren().addAll(leftBox, spacer, rightBox);
        Image deleteImg = new Image(this.getClass().getResourceAsStream("../resources/delete.png"));
        ImageView deleteImgView = new ImageView(deleteImg);
        deleteImgView.setFitHeight(imgSize);
        deleteImgView.setFitWidth(imgSize);
        Image editImg = new Image(this.getClass().getResourceAsStream("../resources/edit.png"));
        ImageView editImgView = new ImageView(editImg);
        editImgView.setFitHeight(imgSize);
        editImgView.setFitWidth(imgSize);

        super.setScaleTransitions(editImgView, bigChange);
        super.setScaleTransitions(deleteImgView, bigChange);

        leftBox.getChildren().addAll(new Label(dilemmaStr), new Label(Short.toString(dilemmaWeek)));
        rightBox.getChildren().addAll(editImgView, deleteImgView);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        //If admin rights aren't set hide edit/delete option
        if(!isAdmin) {
            editImgView.setVisible(false);
            deleteImgView.setVisible(false);
        }

        deleteImgView.setOnMouseClicked( (MouseEvent e ) -> {
            switchToDoubleNotice();
            super.displayPopup("Ouderpaar permanent verwijderen?");
            resultsList.setMouseTransparent(true);
            selectedDilemma = dilemma;
        });

        editImgView.setOnMouseClicked( (MouseEvent e) -> {
            selectedDilemma = dilemma;
            dlc.editDilemma(selectedDilemma);
        });

        return mainBox;

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

    public void handleConfirmDelete() {
        dlc.deleteDilemma(selectedDilemma);
    }

    private ListCell<Dilemma> createListCell() {
        return new ListCell<Dilemma>() {
            @Override
            protected void updateItem(Dilemma dilemma, boolean empty) {
                super.updateItem(dilemma, empty);
                if (empty || dilemma == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(makeRow(dilemma));
                }
            }
        };
    }

    public void addDillemas(List<Dilemma> dilemmas) {
        filteredList = new FilteredList<>(FXCollections.observableArrayList(dilemmas), e->true);
        resultsList.setItems(filteredList);
    }

    public void deleteRow(Dilemma dilemma) {
        ObservableList<Dilemma> list = FXCollections.observableArrayList(filteredList);
        list.remove(dilemma);
        filteredList = new FilteredList<>(list, e->true);
        resultsList.setItems(filteredList);
    }
}

