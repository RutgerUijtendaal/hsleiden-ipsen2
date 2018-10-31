package views;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class BaseView {

    String paneColorGood = "#03142b";
    String paneColorBad  = "#ff0000";

    protected @FXML Button noticeBtn;
    protected @FXML Label noticeLabel;
    protected @FXML StackPane noticePane;

    protected @FXML ImageView logoD;
    protected @FXML ImageView logoU;
    protected @FXML ImageView logoB;
    protected @FXML ImageView logoI;
    protected @FXML ImageView logoO;

    Scene rootScene;

    protected @FXML Pane fillPane;

    public Pane getFillPane() {
        return fillPane;
    }

    public Parent loadFXML(String path) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
            loader.setController(this);
            root = loader.load();
            setScaleTransitions(noticeBtn, 1.1);

            noticePane.setOpacity(0); // incase someone did not do this in their fxml
            noticePane.setMouseTransparent(true); // incase someone did not do this in their fxml
            fillPane.setOpacity(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setCSS("../resources/main.css", root);

        double bigChange = 1.1;

        this.setScaleAndRotateTransitions(logoD, bigChange, -5);
        this.setScaleAndRotateTransitions(logoU, bigChange,-3);
        this.setScaleAndRotateTransitions(logoB, bigChange, 2);
        this.setScaleAndRotateTransitions(logoI, bigChange, 5);
        this.setScaleAndRotateTransitions(logoO, bigChange, 7);
        return root;
    }

    public void displayError(String message) {
        noticeLabel.setText(message);
        noticePane.setStyle(noticePane.getStyle().replace(" -fx-border-color: " + paneColorGood, " -fx-border-color: " + paneColorBad));
        doFadeIn(noticePane);
    }

    public void displayPopup(String message) {
        noticeLabel.setText(message);
        noticePane.setStyle(noticePane.getStyle().replace(" -fx-border-color: " + paneColorBad, " -fx-border-color: " + paneColorGood));
        doFadeIn(noticePane);
    }

    public void hideNotice() {
        doFadeOut(noticePane);
    }

    protected void doFadeIn(Node node) {
        node.setMouseTransparent(false);
        FadeTransition ft = new FadeTransition(Duration.millis(200), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    protected void doFadeOut(Node node) {
        node.setMouseTransparent(true);
        FadeTransition ft = new FadeTransition(Duration.millis(200), node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
    }

    protected void setScaleTransitions(Node node, double sizeIncrease) {

        short durationMillis = 100;

        node.setOnMouseEntered( (MouseEvent e) -> scale(node, durationMillis, sizeIncrease));

        node.setOnMouseExited( (MouseEvent e) -> scale(node, durationMillis, 1.0));
    }

    private void scale(Node node, short durationMillis, double sizeIncrease) {
        ScaleTransition st = new ScaleTransition(Duration.millis(durationMillis), node);
        st.setToX(sizeIncrease);
        st.setToY(sizeIncrease);
        st.play();
    }

    protected void setRotateTransitions(Node node, double angle) {
        short durationMillis = 100;

        node.setOnMouseEntered( (MouseEvent e) -> rotate(node, durationMillis, angle));

        node.setOnMouseExited( (MouseEvent e) -> rotate(node, durationMillis, 0));
    }

    protected void setScaleAndRotateTransitions(Node node, double sizeIncrease, double angle) {
        short durationMillis = 100;

        node.setOnMouseEntered( (MouseEvent e) -> {
            scale(node, durationMillis, sizeIncrease);
            rotate(node, durationMillis, angle);
        });

        node.setOnMouseExited( (MouseEvent e) -> {
            scale(node, durationMillis, 1);
            rotate(node, durationMillis, 0);
        });
    }

    private void rotate(Node node, short durationMillis, double angle) {
        RotateTransition rt = new RotateTransition(Duration.millis(durationMillis), node);
        rt.setToAngle(angle);
        rt.play();
    }


    protected void setCSS(String path, Parent parent) {
        parent.getStylesheets().add(this.getClass().getResource(path).toExternalForm());
    }

    public Scene getScene() {
        return rootScene;
    }
}
