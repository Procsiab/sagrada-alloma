package ClientP2P.Interface;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInScreenController implements Initializable {
    @FXML private ImageView SagradaImage;
    @FXML private Button StartButton;

    @FXML public void LogIn(ActionEvent event) throws IOException {

        Parent root1 = FXMLLoader.load(getClass().getResource("StartGame.fxml"));
        Scene StartedGame = new Scene(root1,400,400);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(StartedGame);
        window.show();
    }


    //Animazioni schermata di Login
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Scale Transition di Sagrada LogIn
        AnimazioneSagrada();
        AnimazioneStartGame();


    }

    private void AnimazioneStartGame() {
        FadeTransition ft = new FadeTransition(Duration.millis(3000), StartButton);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ft.play();

    }

    private void AnimazioneSagrada(){
        ScaleTransition scaleTransition = new ScaleTransition();

        //Setting the duration for the transition
        scaleTransition.setDuration(Duration.millis(5000));

        //Setting the node for the transition
        scaleTransition.setNode(SagradaImage);

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


