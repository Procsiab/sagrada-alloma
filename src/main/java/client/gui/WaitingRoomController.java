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


    // Load cards to be chosen and then then passed into choosenCard variable. This variable is used in ChooseWindowController
    // in order to load the 4 available cards.
    public WaitingRoomController() {
        MainClient.waitingRoomController = this;
    }


    public void chooseWindow(ArrayList<Integer> listaCarte) {
        System.out.println("CHIAMATA IN WAITING ROOM");

        Platform.runLater(
                () -> {
                    System.out.println("DENTRO TRY");
                    MainClient.choosenCards = listaCarte;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChooseWindow.fxml"));
                    try {
                        Parent root1 = loader.load();
                        System.out.println("AFTER LOADER");
                        Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
                        Stage window = (Stage) paneTest.getScene().getWindow();
                        System.out.println("AFTER STAGE");
                        window.setScene(startedGame);
                        System.out.println("AFTER setSCENE");
                        window.show();
                        System.out.println("AFTER WINDOW SHOW");
                    } catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                }
        );


    }

   /* @FXML
    private void testLoad(ActionEvent event) throws IOException{
        ArrayList testArray = new ArrayList<Integer>();
        Collections.addAll(testArray, 1, 2, 4, 6);


        chooseWindow( testArray );



    } */

}