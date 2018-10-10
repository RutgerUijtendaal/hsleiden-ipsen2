package views;

import javafx.fxml.FXML;
//import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import controllers.CoupleListController;

public class CoupleListView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button searchBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;

    private @FXML ListView<HBox> resultsList;

    private @FXML ImageView logoD;
    private @FXML ImageView logoU;
    private @FXML ImageView logoB;
    private @FXML ImageView logoI;
    private @FXML ImageView logoO;

    private CoupleListController clc;

    private ObservableList<HBox> listData;

    public CoupleListView(CoupleListController clc) {
        this.clc = clc;
        rootFXML = super.loadFXML("../fxml/parent_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
        double bigChange = 1.1;
            
        super.setScaleTransitions(searchBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

        super.setScaleTransitions(email, smallChange);

        super.setScaleTransitions(logoD, bigChange);
        super.setScaleTransitions(logoU, bigChange);
        super.setScaleTransitions(logoB, bigChange);
        super.setScaleTransitions(logoI, bigChange);
        super.setScaleTransitions(logoO, bigChange);

        listData = FXCollections.observableArrayList();

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        HBox mainBox = new HBox();
        HBox rightBox = new HBox();
        VBox leftBox = new VBox();
        mainBox.setStyle("-fx-background-color: red");
        rightBox.setStyle("-fx-background-color: yellow");
        leftBox.setStyle("-fx-background-color: green");
        mainBox.getChildren().addAll(leftBox, region1, rightBox);


        leftBox.getChildren().addAll(new Label("ouder1@jemoeder.net"), new Label("ouder2@coolapplicatie.com"));
        rightBox.getChildren().add(new Label("delete dis"));
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        listData.add(mainBox);
        resultsList.setItems(listData);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleSearchBtnClick() {
        System.out.println("running handleSearchBtnClick from CoupleListView");
        clc.handleSearchBtnClick(email.getText());
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from CoupleListView");
        clc.handleBackBtnClick();
    }

    public void setListData(ObservableList<HBox> listData) {
        this.listData = listData;
        resultsList.setItems(listData);
    }

}

