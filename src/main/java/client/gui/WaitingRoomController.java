package client.gui;

import client.MainClient;
import client.ProxyClient;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WaitingRoomController implements Initializable {

    @FXML
    private AnchorPane paneBackground;
    @FXML
    private ImageView outerCircle;
    @FXML
    private ImageView bar1,bar2,bar3,bar4,bar5;

    private ArrayList<ImageView> bars = new ArrayList<>();
    private ProxyClient proxyClient = ProxyClient.getInstance();




    public WaitingRoomController() {
        MainClient.waitingRoomController = this;
    }

    public void initialize(URL location, ResourceBundle resources) {
        loadArray();
        rotateTransition();
        int wait=0;
        for(int i=0; i<bars.size();i++){
            barTransitions(wait, bars.get(i));
            wait=wait+100;


        }


    }
    public void chooseWindow(ArrayList<Integer> listCard) {

        Platform.runLater(
                () -> {
                    
                    MainClient.choosenCards = listCard;

                    // Loading of ChooseWindow view, where you can choose your own map
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChooseWindow.fxml"));
                    try {
                        Parent root1 = loader.load();
                        Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
                        Stage window = (Stage) paneBackground.getScene().getWindow();
                        window.setScene(startedGame);
                        window.show();
                    } catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                }
        );


    }
    private  void loadArray(){
        bars.add(bar1);
        bars.add(bar2);
        bars.add(bar3);
        bars.add(bar4);
        bars.add(bar5);

    }
    private void rotateTransition(){
        RotateTransition rt = new RotateTransition(Duration.millis(3000),outerCircle );
        rt.setByAngle(360);
        rt.setCycleCount(100);
        rt.play();
    }

    private void barTransitions(int attesa, ImageView bar ){
        PauseTransition pt = new PauseTransition(Duration.millis(attesa));
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1));
        tt.setNode(bar);
        tt.setByY(50);
        tt.setAutoReverse(true);
        tt.setCycleCount(100);
        SequentialTransition seqT = new SequentialTransition (bar, pt, tt);
        seqT.play();

    }

    @FXML
    private void disconnect(ActionEvent event){
        proxyClient.exitGame1();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogInScreen.fxml"));
        try {
            Parent root1 = loader.load();
            Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
            Stage window = (Stage) paneBackground.getScene().getWindow();
            window.setScene(startedGame);
            window.show();
        } catch (IOException Exception) {
            System.out.println("View not found. Error while loading");

        }





    }

}