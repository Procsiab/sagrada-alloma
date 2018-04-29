package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.logic.ConcurrencyManager;
import client.threads.GameHelper;
import shared.network.ConnectionNetwork;
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
        // Create ConnectionNetwork singleton to setup RMI networking
        ConnectionNetwork.setConnection(new NetworkRmi("", 0));
        MainClient.game = new GameHelper();
        ConcurrencyManager.submit(game);
        launch(args);

        // Close connection when window closes
        /*System.out.println("Send 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }*/
        System.exit(0);
    }
}