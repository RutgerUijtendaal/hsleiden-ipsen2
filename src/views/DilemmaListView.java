package views;


import controllers.DilemmaListController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.Dilemma;

import java.util.List;

public class DilemmaListView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button backBtn;
    private @FXML Button noticeYesBtn;

    private @FXML TextField dilemmaSearch;

    private @FXML Button addDilemmaBtn;

    private @FXML ListView<Dilemma> resultsList;
    private FilteredList<Dilemma> filteredList;

    private DilemmaListController dilemmaListController;

    private Dilemma selectedDilemma;

    private Boolean isAdmin = false;

    double smallChange = 1.05;
    double bigChange = 1.15;

    public DilemmaListView(DilemmaListController dilemmaListController) {
        this.dilemmaListController = dilemmaListController;
        rootFXML = super.loadFXML("/fxml/dilemma_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
        addTransitions();
        addFilter();
        resultsList.setCellFactory(lv -> createListCell());
    }

    private void addTransitions() {
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(dilemmaSearch, smallChange);
        super.setScaleTransitions(addDilemmaBtn, smallChange);
    }

    private void addFilter() {
        dilemmaSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(dilemma ->{
            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            if(dilemma.getTheme().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }else return Integer.toString(dilemma.getWeekNr()).contains(lowerCaseFilter);
        }));
    }

    public void setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
        setupAdminView();
    }

    public void handleBackBtnClick() {
        dilemmaListController.handleBackBtnClick();
    }

    public void handleAddDilemmaBtnClick() { dilemmaListController.handleAddDilemmaBtnClick(); }

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
        Image deleteImg = new Image(this.getClass().getResourceAsStream("/resources/delete.png"));
        ImageView deleteImgView = new ImageView(deleteImg);
        deleteImgView.setFitHeight(imgSize);
        deleteImgView.setFitWidth(imgSize);
        HBox imageBox = new HBox();
        Image editImg = new Image(this.getClass().getResourceAsStream("/resources/edit.png"));
        ImageView editImgView = new ImageView(editImg);
        editImgView.setFitHeight(imgSize);
        editImgView.setFitWidth(imgSize);
        editImgView.setPickOnBounds(false);

        imageBox.getChildren().add(editImgView);

        super.setScaleTransitions(imageBox, bigChange);
        super.setScaleTransitions(deleteImgView, bigChange);

        leftBox.getChildren().addAll(new Label(dilemmaStr), new Label(Short.toString(dilemmaWeek)));
        rightBox.getChildren().addAll(imageBox, deleteImgView);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        //If admin rights aren't set hide edit/delete option
        if(!isAdmin) {
            imageBox.setVisible(false);
            deleteImgView.setVisible(false);
        }

        deleteImgView.setOnMouseClicked( (MouseEvent e ) -> {
            switchToDoubleNotice();
            super.displayPopup("Dilemma permanent verwijderen?");
            resultsList.setMouseTransparent(true);
            selectedDilemma = dilemma;
        });

        imageBox.setOnMouseClicked( (MouseEvent e) -> {
            selectedDilemma = dilemma;
            dilemmaListController.editDilemma(selectedDilemma);
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
        dilemmaListController.deleteDilemma(selectedDilemma);
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

    private void setupAdminView() {
        if(isAdmin) {
            addDilemmaBtn.setVisible(true);
            dilemmaSearch.setMaxWidth(400);
            dilemmaSearch.setTranslateX(0.0);
        }
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

