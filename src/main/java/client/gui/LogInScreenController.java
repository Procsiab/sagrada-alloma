package client.gui;
import client.MainClient;
import client.ProxyClient;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInScreenController implements Initializable {

    // Logic Variables
    private String nickname;
    // GUI Variables
    @FXML private ImageView sagradaImage;
    @FXML private Button startButton;
    @FXML private TextField nicknameField;



    public LogInScreenController(){
        MainClient.logInScreenController = this;
    }

    // Initialization
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animationSagrada();
        animationStartGame();
        startButton.setStyle("-fx-background-color: transparent;");

    }

    @FXML public void LogIn(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WaitingRoom.fxml"));
        Parent root1 = loader.load();


        String message1 = ProxyClient.getInstance().startGame(nickname);
        Logger.log("Server responded as: " + message1);

        if (message1.equals("Connections successful. Please wait for other players to connect")) {
            Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(startedGame);
            window.show();
        }
        if (message1.equals("You already playing! Hold on while the server calls you again")){
        //todo: start StartGameController
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
            Parent root2  = loader1.load();
            StartGameController controller = loader1.getController();

            Scene startedGame2 = new Scene(root2, 1280, 800, Color.WHITE);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(startedGame2);
            window.setOnHidden(e -> controller.shutdown());

            window.show();
            }
            if(message1.equals("NickName is not available.")){
            CustomAlert notAvailable = new CustomAlert(Alert.AlertType.WARNING,message1,"Please select another nickname!");

            }


    }


    // Support methods for animation
    private void animationStartGame() {
        FadeTransition ft = new FadeTransition(Duration.millis(3000), startButton);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ft.play();

    }

    private void animationSagrada(){
        ScaleTransition scaleTransition = new ScaleTransition();

        //Setting the duration for the transition
        scaleTransition.setDuration(Duration.millis(5000));

        //Setting the node for the transition
        scaleTransition.setNode(sagradaImage);

        //Setting the dimensions for scaling
        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);

        //Setting the cycle count for the translation
        scaleTransition.setCycleCount(1);

        //Setting auto reverse value to true
        scaleTransition.setAutoReverse(false);

        //Playing the animation
        scaleTransition.play();


    }

    @FXML
    private void saveNickname(ActionEvent event){
       nickname = nicknameField.getText();

    }




}


