package client.gui;

import client.MainClient;
import client.MiddlewareClient;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shared.Logger;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class WaitingRoomController {

    @FXML
    private AnchorPane paneTest;



    public WaitingRoomController() {
        MainClient.waitingRoomController = this;
    }


    public void chooseWindow(ArrayList<Integer> listaCarte) {
        System.out.println("CHIAMATA IN WAITING ROOM");

        Platform.runLater(
                () -> {
                    
                    MainClient.choosenCards = listaCarte;

                    // Loading of ChooseWindow view, where you can choose your own map
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChooseWindow.fxml"));
                    try {
                        Parent root1 = loader.load();
                        Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
                        Stage window = (Stage) paneTest.getScene().getWindow();
                        window.setScene(startedGame);
                        window.show();
                    } catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                }
        );


    }

}