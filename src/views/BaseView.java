package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class BaseView {

    @FXML Button noticeBtn;
    @FXML Label noticeLabel;
    @FXML StackPane noticePane;

    public Parent loadFXML(String path) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
            loader.setController(this);
            root = loader.load();
            setScaleTransitions(noticeBtn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public void displayError(String message) {
    }

    public void displayPopup(String message) {
        noticeLabel.setText(message);
        noticePane.setVisible(true);
    }

    public void hideNotice() {
        noticePane.setVisible(false);
    }

    protected void setScaleTransitions(Node node) {

        double sizeIncrease = 1.05;
        short durationMillis = 100;

        node.setOnMouseEntered( (MouseEvent e) -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(durationMillis), node);
            st.setToX(sizeIncrease);
            st.setToY(sizeIncrease);
            st.play();
        });

        node.setOnMouseExited( (MouseEvent e) -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(durationMillis), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

    }


}
