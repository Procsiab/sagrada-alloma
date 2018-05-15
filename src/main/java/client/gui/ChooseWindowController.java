package client.gui;

import client.MainClient;
import client.MiddlewareClient;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shared.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseWindowController implements Initializable {
    @FXML private Button card1,card2,card3,card4;
    private Integer  tempSelectedCard, selectedCard;
    private MiddlewareClient middlewareClient = MiddlewareClient.getInstance();
    @FXML  private AnchorPane chooseWindowPane;
    public ChooseWindowController() {
        MainClient.chooseWindowController = this;
    }


    @FXML
    public void chooseWindow(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
        Parent root1 = loader.load();
        System.out.println("Button pressed " + ((Button) event.getSource()).getId());
        tempSelectedCard = tempSelectedCard.parseInt(((Button) event.getSource()).getText());
        //System.out.println(MainClient.choosenCards.get(1));
        selectedCard = MainClient.choosenCards.get(tempSelectedCard-1);

        middlewareClient.chooseWindowBack(selectedCard);

        System.out.println(selectedCard);

        Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(startedGame);
        window.show();

    }


    public void startGameViewForced(){
        Logger.log("gameView starts now");
        //todo: implement here forced method
        Platform.runLater(
                () -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
                    try{
                    Parent root1 = loader.load();
                    Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
                    Stage window = (Stage) chooseWindowPane.getScene().getWindow();
                    window.setScene(startedGame);
                    window.show();}
                    catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                }
        );

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
        card1.setStyle("-fx-background-image: url('Window"+i+".png');-fx-background-size: 100% 100% ;");
        card2.setStyle("-fx-background-image: url('Window"+j+".png');-fx-background-size: 100% 100%;");
        card3.setStyle("-fx-background-image: url('Window"+k+".png');-fx-background-size: 100% 100%;");
        card4.setStyle("-fx-background-image: url('Window"+g+".png');-fx-background-size: 100% 100%;");


    }



}