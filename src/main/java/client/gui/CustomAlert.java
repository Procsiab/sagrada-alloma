package client.gui;

import javafx.scene.control.Alert;

/**
 Simple class used to wrap and make more re-usable alerts. It's called in different parts of the GUI to show error or warnings.
 */
public class CustomAlert {
    public CustomAlert(Alert.AlertType type, String title, String text){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();

    }
}
