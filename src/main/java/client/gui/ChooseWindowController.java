package client.gui;

import client.MainClient;
import client.ProxyClient;
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


/**
 This class is the controller associated with {@code WaitingRoom.fxml}. It allows the user to select a mapcard, then it returns the selected card to the {@code model}
 via {@link ProxyClient#chooseWindowBack(Integer)} and loads the {@link StartGameController} + view of the match. If no cards is selected in a determined amount of time,
 the model forces the starting of the game calling {@link this#startGameViewForced()} and a card is automatically assigned to the player by the {@code model}.
 */
public class ChooseWindowController implements Initializable {

    // Logic Variables
    private Integer  tempSelectedCard, selectedCard;
    private ProxyClient proxyClient = ProxyClient.getInstance();

    // GUI Variables
    @FXML private Button card1,card2,card3,card4;
    @FXML  private AnchorPane chooseWindowPane;

    public ChooseWindowController() {
        MainClient.setChooseWindowController(this);
    }


    public void initialize(URL location, ResourceBundle resources) {
        loadCards();
        }
    /**
    The method is triggered by clicking one of the four buttons associated with a MapCard. Once the card has been selected, it's passed to the model via {@code chooseWindowBack(...)},
     then it loads the new view.
     */

    @FXML
    public void chooseWindow(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
        Parent root = loader.load();
        System.out.println("Button pressed " + ((Button) event.getSource()).getId());
        tempSelectedCard = tempSelectedCard.parseInt(((Button) event.getSource()).getText());
        selectedCard = MainClient.getChosenCards().get(tempSelectedCard-1);
        proxyClient.chooseWindowBack(selectedCard);
        System.out.println(selectedCard);
        StartGameController controller = loader.getController();

        Scene startedGame = new Scene(root, 1280, 800, Color.WHITE);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(startedGame);
        window.setOnHidden(e -> controller.shutdown());

        window.show();

    }

    /**
     Method called by {@link ProxyClient#startGameViewForced()} whenever the user doesn't select a card in a proper amount of time. It just load the view of the game
     */
    public void startGameViewForced(){
        Logger.log("gameView starts now");
        Platform.runLater(
                () -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
                    try{
                    Parent root = loader.load();
                    StartGameController controller = loader.getController();
                    Scene startedGame = new Scene(root, 1280, 800, Color.WHITE);
                    Stage window = (Stage) chooseWindowPane.getScene().getWindow();
                    window.setScene(startedGame);
                    window.setOnHidden(e -> controller.shutdown());
                    window.show();}
                    catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                }
        );

    }
    /**
     Retrieves the available cards from the {@link MainClient#chosenCards}, then sets the corresponding image as background of the buttons.
     */
    private void loadCards(){
        int i,j,k,g;
        i = MainClient.getChosenCards().get(0);
        j = MainClient.getChosenCards().get(1);
        k = MainClient.getChosenCards().get(2);
        g = MainClient.getChosenCards().get(3);
        // IMPORTANT: use "+ variable +" in order to use a variable and not a fixed value!
        card1.setStyle("-fx-background-image: url('Windows/Window"+i+".png');-fx-background-size: 100% 100% ;");
        card2.setStyle("-fx-background-image: url('Windows/Window"+j+".png');-fx-background-size: 100% 100%;");
        card3.setStyle("-fx-background-image: url('Windows/Window"+k+".png');-fx-background-size: 100% 100%;");
        card4.setStyle("-fx-background-image: url('Windows/Window"+g+".png');-fx-background-size: 100% 100%;");


    }



}