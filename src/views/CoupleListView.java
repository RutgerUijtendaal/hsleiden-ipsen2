package views;

import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
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
import util.CoupleListRow;
import models.CoupleListModel;

public class CoupleListView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button searchBtn;
    private @FXML Button backBtn;

    private @FXML Button noticeYesBtn;

    private @FXML TextField email;

    private @FXML ListView<CoupleListRow> resultsList;

    private CoupleListController clc;

    private ObservableList<CoupleListRow> listData;

    private ImageView currentlySelectedImageView;

    double smallChange = 1.05;
    double bigChange = 1.1;

    public CoupleListView(CoupleListController clc) {
        this.clc = clc;
        rootFXML = super.loadFXML("../fxml/parent_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
            
        super.setScaleTransitions(searchBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

        super.setScaleTransitions(email, smallChange);

        listData = FXCollections.observableArrayList();
        resultsList.setItems(listData);

    }


    public Scene getViewScene() {
        return rootScene;
    }

    public void handleConfirmDelete() {
        System.out.println("GOTTA DELETE DESE NIGGAS");
        HBox firstParent = (HBox)currentlySelectedImageView.getParent();
        CoupleListRow mainBox = (CoupleListRow)firstParent.getParent();
        CoupleListModel couple = mainBox.getCouple();
        int couple_id = couple.getCoupleId();
        models.Parent parent1 = couple.getParent1();
        models.Parent parent2 = couple.getParent2();
        clc.deleteCouple(couple_id, parent1, parent2);
    }

    public void handleSearchBtnClick() {
        System.out.println("running handleSearchBtnClick from CoupleListView");
        clc.handleSearchBtnClick(email.getText());
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from CoupleListView");
        clc.handleBackBtnClick();
    }

    public void clearListData() {
        listData.clear();
    }

    public void addSingleRow(CoupleListModel couple) {

        String email1 = couple.getParent1().getEmail();
        String phoneNr1 = couple.getParent1().getPhoneNr();
        String email2 = couple.getParent2().getEmail();
        String phoneNr2 = couple.getParent2().getPhoneNr();

        Region spacer = new Region();
        CoupleListRow mainBox = new CoupleListRow(couple);
        HBox deleteBox = new HBox();
        VBox phoneNrBox = new VBox();
        VBox emailBox = new VBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        emailBox.setSpacing(1);
        phoneNrBox.setSpacing(1);

        mainBox.getChildren().addAll(emailBox, spacer, phoneNrBox, deleteBox);
        Image deleteImg = new Image(this.getClass().getResourceAsStream("../resources/delete.png"));
        ImageView deleteImgView = new ImageView(deleteImg);
        deleteImgView.setFitHeight(50);
        deleteImgView.setFitWidth(50);
        phoneNrBox.setPadding(new Insets(0,10,0,10));
        super.setScaleTransitions(deleteImgView, bigChange);

        emailBox.getChildren().addAll(new Label(email1), new Label(email2));
        phoneNrBox.getChildren().addAll(new Label(phoneNr1), new Label(phoneNr2));
        deleteBox.getChildren().add(deleteImgView);
        deleteBox.setAlignment(Pos.CENTER_RIGHT);

        listData.add(mainBox);

        deleteImgView.setOnMouseClicked( (MouseEvent e ) -> {
            super.displayPopup("m8, u sure bout dis?");
            currentlySelectedImageView = deleteImgView;
        });

    }

}

