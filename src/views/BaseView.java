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

import java.util.Objects;

/**
 * Base class for all the views
 *
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 */
@SuppressWarnings("ALL")
public class BaseView {

    private final String paneColorGood = "#03142b";
    private final String paneColorBad  = "#ff0000";

    @FXML Button noticeBtn;
    @FXML
    private Label noticeLabel;
    @FXML StackPane noticePane;

    @FXML
    private ImageView logoD;
    @FXML
    private ImageView logoU;
    @FXML
    private ImageView logoB;
    @FXML
    private ImageView logoI;
    @FXML
    private ImageView logoO;

    Scene rootScene;

    @FXML Pane fillPane;

    /**
     * Gets the pane responsible for the fade
     * @return a pane responsible for the fade
     */
    public Pane getFillPane() {
        return fillPane;
    }

    /**
     * Loads a fxml file
     * @param path Path to a fxml file
     * @return Elements containing everything the fxml file contains
     */
    Parent loadFXML(String path) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(path));
            loader.setController(this);
            root = loader.load();
            setScaleTransitions(noticeBtn, 1.1);

            noticePane.setOpacity(0); // incase someone did not do this in their fxml
            noticePane.setMouseTransparent(true); // incase someone did not do this in their fxml
            fillPane.setOpacity(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setCSS("/resources/main.css", Objects.requireNonNull(root));

        double bigChange = 1.1;

        this.setScaleAndRotateTransitions(logoD, bigChange, -5);
        this.setScaleAndRotateTransitions(logoU, bigChange,-3);
        this.setScaleAndRotateTransitions(logoB, bigChange, 2);
        this.setScaleAndRotateTransitions(logoI, bigChange, 5);
        this.setScaleAndRotateTransitions(logoO, bigChange, 7);
        return root;
    }

    /**
     * Displays an error message
     * @param message The error massage to show
     */
    public void displayError(String message) {
        noticeLabel.setText(message);
        noticePane.setStyle(noticePane.getStyle().replace(" -fx-border-color: " + paneColorGood, " -fx-border-color: " + paneColorBad));
        doFadeIn(noticePane);
    }

    /**
     * Displays a popup
     * @param message The message in a popup
     */
    public void displayPopup(String message) {
        noticeLabel.setText(message);
        noticePane.setStyle(noticePane.getStyle().replace(" -fx-border-color: " + paneColorBad, " -fx-border-color: " + paneColorGood));
        doFadeIn(noticePane);
    }

    /**
     * Hides the popup
     */
    public void hideNotice() {
        doFadeOut(noticePane);
    }

    /**
     * Fades the view in when switching scenes
     */
    private void doFadeIn(Node node) {
        node.setMouseTransparent(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    /**
     * Fades the view out when switching scenes
     */
    void doFadeOut(Node node) {
        node.setMouseTransparent(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    /**
     * Binds an scale transition to a element
     * @param node Element to add the transition
     * @param sizeIncrease the amount a element has to increase
     */
    void setScaleTransitions(Node node, double sizeIncrease) {

        short durationMillis = 100;

        node.setOnMouseEntered( (MouseEvent e) -> scale(node, durationMillis, sizeIncrease));

        node.setOnMouseExited( (MouseEvent e) -> scale(node, durationMillis, 1.0));
    }

    /**
     * Makes an scale transition
     * @param node Element to add the transition
     * @param durationMillis duration the transition takes
     * @param sizeIncrease the amount a element has to increase
     */
    private void scale(Node node, short durationMillis, double sizeIncrease) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(durationMillis), node);
        transition.setToX(sizeIncrease);
        transition.setToY(sizeIncrease);
        transition.play();
    }

    /**
     * Binds an scale and rotate transition to a element
     * @param node Element to add the transition
     * @param sizeIncrease the amount a element has to increase
     * @param angle to rotate the element
     */
    private void setScaleAndRotateTransitions(Node node, double sizeIncrease, double angle) {
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

    /**
     * Makes an rotate transition
     * @param node Element to add the transition
     * @param durationMillis duration the transition takes
     * @param angle the angle for the transition
     */
    private void rotate(Node node, short durationMillis, double angle) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(durationMillis), node);
        rotateTransition.setToAngle(angle);
        rotateTransition.play();
    }

    /**
     * Adds css to a parent
     * @param path path to the css file
     * @param parent Parent to add the css to
     */
    private void setCSS(String path, Parent parent) {
        parent.getStylesheets().add(this.getClass().getResource(path).toExternalForm());
    }

    /**
     * Return the scene from a view
     * @return a Scene
     */
    public Scene getScene() {
        return rootScene;
    }
}
