package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.logic.ConcurrencyManager;
import client.threads.GameHelper;
import shared.network.rmi.NetworkRmi;

public class MainClient extends Application {
    public static GameHelper game;
    //here goes the type of actual connection. Please note that this is static so that it can be
    //accessed with sth like that: MainClient.connectionPerformed...
    //public static Connections connectionPerformed;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void main(String [] args) {
        //TODO Let the player choose the connection type through GUI
        MiddlewareClient.setConnection(new NetworkRmi(""));

        MainClient.game = new GameHelper();
        ConcurrencyManager.submit(game);
        launch(args);
    }
}