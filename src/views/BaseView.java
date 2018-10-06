package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BaseView {

    public Parent loadFXML(String path) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
            loader.setController(this);
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public void displayError(String message) {
    }

    public void displayPopup(String message) {
    }
}
