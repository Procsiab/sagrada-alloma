package client.gui;
import client.MainClient;
import client.network.NetworkRmiClient;
import client.threads.GameHelper;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.Logger;
import shared.SharedServerMatchManager;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LogInScreenController implements Initializable {

    private SharedServerMatchManager netMatchManager;
    @FXML private ImageView sagradaImage;
    @FXML private Button startButton;
    private StartGameController gameClient;
    private GameHelper game;

    @FXML public void LogIn(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartGame.fxml"));
        Parent root1 = loader.load();
        this.game = MainClient.game;

        // Trova l'istanza di StartGameController, per poter runnare il metodo di update della view dall'esterno. Chiedere come passarlo all'esterno.
        StartGameController controllerGame = loader.getController();
        this.gameClient = controllerGame;

        // Passo il mio controller all'esterno, va bene fatto così?

        game.setGraphics(controllerGame);

        // Fine instanziamento

        String message1 = new String();

        System.out.println("You clicked me");
        this.netMatchManager = NetworkRmiClient.getInstance().getExportedObject("MatchManager");
        try {
            message1 = this.netMatchManager.startGame(gameClient);
        } catch (RemoteException re) {
            Logger.log("Error calling method on remote object!");
            Logger.strace(re);
        }

        System.out.println(message1);

        if (message1.equals("Connections successful. Please wait for other players to connect")) {
            Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(startedGame);
            window.show();
        }
    }


    //Animazioni schermata di Login
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Scale Transition di Sagrada LogIn
        animazioneSagrada();
        animazioneStartGame();


    }

    private void animazioneStartGame() {
        FadeTransition ft = new FadeTransition(Duration.millis(3000), startButton);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ft.play();

    }

    private void animazioneSagrada(){
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

}


