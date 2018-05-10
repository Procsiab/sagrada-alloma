package client.gui;

import client.MainClient;
import client.MiddlewareClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shared.Logger;

import java.io.IOException;

public class WaitingRoomController {



    @FXML
    public void chooseWindow(ActionEvent event) throws IOException {





        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
        Parent root1 = loader.load();

            Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(startedGame);
            window.show();

    }
}
