package client.gui;

import client.MainClient;
import client.MiddlewareClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shared.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitingRoomController implements Initializable {
    @FXML private Button card1,card2,card3,card4;
    private String selectedCard ;
    public WaitingRoomController() {
        MainClient.waitingRoomController = this;
    }


    @FXML
    public void chooseWindow(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
        Parent root1 = loader.load();
        System.out.println("Button pressed " + ((Button) event.getSource()).getId());
        selectedCard = ((Button) event.getSource()).getId();
        System.out.println(selectedCard);
        Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(startedGame);
        window.show();

    }

    public void initialize(URL location, ResourceBundle resources) {
        // Add transition and other stuff
        loadCards();

    }

    private void loadCards(){
       // TODO: Implement call to method which return the 4 randomic cards chosen by server.

       // Backround set to test
        card1.setStyle("-fx-background-image: url('1.png');-fx-background-size: 100% 100% ;");
        card2.setStyle("-fx-background-image: url('2.png');-fx-background-size: 100% 100%;");
        card3.setStyle("-fx-background-image: url('3.png');-fx-background-size: 100% 100%;");
        card4.setStyle("-fx-background-image: url('4.png');-fx-background-size: 100% 100%;");


    }



}