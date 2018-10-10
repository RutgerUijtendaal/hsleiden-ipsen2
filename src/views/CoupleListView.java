package views;



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

    double smallChange = 1.05;
    double bigChange = 1.1;

    public CoupleListView(CoupleListController clc) {
        this.clc = clc;
        rootFXML = super.loadFXML("../fxml/parent_list.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
            
        super.setScaleTransitions(searchBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

        super.setScaleTransitions(email, smallChange);

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
        clc.handleSearchBtnClick(email.getText());
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from CoupleListView");
        clc.handleBackBtnClick();
    }

    public void clearListData() {
        listData.clear();
    }

    private void getEmailFromClick(ImageView deleteImgView) {
        HBox firstParent = (HBox)deleteImgView.getParent();
        HBox secondParent = (HBox)firstParent.getParent();
        VBox emailParent = (VBox)secondParent.getChildren().get(0);
        Label emailLabel1 = (Label)emailParent.getChildren().get(0);
        Label emailLabel2 = (Label)emailParent.getChildren().get(1);
        System.out.println("------------------------------");
        System.out.println(emailLabel1.getText());
        System.out.println(emailLabel2.getText());
        System.out.println("------------------------------");
    }

    public void addSingleRow(models.Parent parent1, models.Parent parent2) { // THIS COULD ALSO JUST TAKE PARENTS FOR EASIER GETTING OF THE ID

        String email1 = parent1.getEmail();
        String email2 = parent2.getEmail();

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
        super.setScaleTransitions(deleteImgView, bigChange);

        leftBox.getChildren().addAll(new Label(email1), new Label(email2));
        rightBox.getChildren().add(deleteImgView);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        listData.add(mainBox);

        deleteImgView.setOnMouseClicked( (MouseEvent e ) -> {
            getEmailFromClick(deleteImgView);
        });

    }

}

