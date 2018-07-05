package client.gui;
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


/**
 * <p>This class is the controller of the first view launched. It's associated with LogInScreen.fxml. The class allows you to enter a nickname and then start to queue for a game</p>
 *
 *
 */
public class LogInScreenController implements Initializable {

    // Logic Variables
    private String nickname;
    // GUI Variables
    @FXML private ImageView sagradaImage;
    @FXML private Button startButton;
    @FXML private TextField nicknameField;


    /**
     * Initialization of view, starts a simple transition and sets button color.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animationSagrada();
        startButton.setStyle("-fx-background-color: transparent;");

    }

    /**
     * Method triggered when the {@code startButton} is clicked. It calls the {@link ProxyClient#getInstance().startGame}, then, based on the response of this method, it starts
     * the queue (moving to the {@link WaitingRoomController}) or return an error and creates a {@link CustomAlert} with the error message.
     */
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
            new CustomAlert(Alert.AlertType.WARNING,message1,"Please select another nickname!");

            }
        if(message1.equals("Please enter a valid NickName")){
            new CustomAlert(Alert.AlertType.WARNING,message1,"Please enter a valid NickName");

        }




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


