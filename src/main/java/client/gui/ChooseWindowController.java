package client.gui;

import client.MainClient;
import com.sun.tools.javac.Main;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseWindowController implements Initializable {
    @FXML private Button card1,card2,card3,card4;
    private String selectedCard ;
    public ChooseWindowController() {
        MainClient.chooseWindowController = this;
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
        int i,j,k,g;
        i = MainClient.choosenCards.get(0);
        j = MainClient.choosenCards.get(1);
        k = MainClient.choosenCards.get(2);
        g = MainClient.choosenCards.get(3);



        // IMPORTANT: use "+ variable +" in order to use a variable and not a fixed value!
       // Backround set to test
        card1.setStyle("-fx-background-image: url('"+i+".png');-fx-background-size: 100% 100% ;");
        card2.setStyle("-fx-background-image: url('"+j+".png');-fx-background-size: 100% 100%;");
        card3.setStyle("-fx-background-image: url('"+k+".png');-fx-background-size: 100% 100%;");
        card4.setStyle("-fx-background-image: url('"+g+".png');-fx-background-size: 100% 100%;");


    }



}