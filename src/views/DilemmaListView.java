package views;



import controllers.DilemmaListController;
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

    private @FXML Button searchBtn;
    private @FXML Button backBtn;

    private @FXML TextField dilemmaSearch;

    private @FXML ListView<Dilemma> resultsList;

    private DilemmaListController dlc;

    double smallChange = 1.05;
    double bigChange = 1.1;

    public DilemmaListView(DilemmaListController dlc) {
        this.dlc = dlc;
        rootFXML = super.loadFXML("../fxml/dilemma_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        super.setScaleTransitions(searchBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

        super.setScaleTransitions(dilemmaSearch, smallChange);

        dilemmaSearch.setOnKeyPressed( (KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleSearchBtnClick();
            }
        });

        resultsList.setCellFactory(lv -> createListCell());
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleSearchBtnClick() {
        dlc.handleSearchBtnClick(dilemmaSearch.getText());
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from DilemmaListView");
        dlc.handleBackBtnClick();
    }

    public void clearListData() {
        resultsList.getItems().clear();
    }

    public HBox makeRow(Dilemma dilemma) {

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
        deleteImgView.setFitHeight(50);
        deleteImgView.setFitWidth(50);
        Image editImg = new Image(this.getClass().getResourceAsStream("../resources/edit.png"));
        ImageView editImgView = new ImageView(editImg);
        editImgView.setFitHeight(50);
        editImgView.setFitWidth(50);

        super.setScaleTransitions(editImgView, bigChange);
        super.setScaleTransitions(deleteImgView, bigChange);

        leftBox.getChildren().addAll(new Label(dilemmaStr), new Label(Short.toString(dilemmaWeek)));
        rightBox.getChildren().addAll(editImgView, deleteImgView);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        //listData.add(mainBox);

        deleteImgView.setOnMouseClicked( (MouseEvent e ) -> {
            System.out.println(id);
        });

        return mainBox;

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
        resultsList.getItems().setAll(dilemmas);
    }
}

