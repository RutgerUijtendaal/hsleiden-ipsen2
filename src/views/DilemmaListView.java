package views;



import controllers.DilemmaListController;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import controllers.CoupleListController;
import models.Answer;
import models.Dilemma;

public class DilemmaListView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button searchBtn;
    private @FXML Button backBtn;

    private @FXML TextField dilemmaSearch;

    private @FXML ListView<HBox> resultsList;

    private @FXML ImageView logoD;
    private @FXML ImageView logoU;
    private @FXML ImageView logoB;
    private @FXML ImageView logoI;
    private @FXML ImageView logoO;

    private DilemmaListController dlc;

    private ObservableList<HBox> listData;

    double smallChange = 1.05;
    double bigChange = 1.1;

    public DilemmaListView(DilemmaListController dlc) {
        this.dlc = dlc;
        rootFXML = super.loadFXML("../fxml/dilemma_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        super.setScaleTransitions(searchBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

        super.setScaleTransitions(dilemmaSearch, smallChange);

        super.setScaleTransitions(logoD, bigChange);
        super.setScaleTransitions(logoU, bigChange);
        super.setScaleTransitions(logoB, bigChange);
        super.setScaleTransitions(logoI, bigChange);
        super.setScaleTransitions(logoO, bigChange);

        listData = FXCollections.observableArrayList();
        resultsList.setItems(listData);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleSearchBtnClick() {
        System.out.println("running handleSearchBtnClick from CoupleListView");
        dlc.handleSearchBtnClick(dilemmaSearch.getText());
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from CoupleListView");
        dlc.handleBackBtnClick();
    }

    public void clearListData() {
        listData.clear();
    }

    public void addSingleRow(Dilemma dilemma) {

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

        leftBox.getChildren().addAll(new Label(dilemmaStr), new Label(Short.toString(dilemmaWeek)));
        rightBox.getChildren().addAll(editImgView, deleteImgView);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        listData.add(mainBox);

        deleteImgView.setOnMouseClicked( (MouseEvent e ) -> {
            System.out.println(id);
        });

    }

}

