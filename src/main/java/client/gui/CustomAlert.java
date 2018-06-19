package client.gui;

import javafx.scene.control.Alert;

public class CustomAlert {
    public CustomAlert(Alert.AlertType type, String title, String text){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();

    }
}
