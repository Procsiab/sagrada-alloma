package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.network.NetworkRmiClient;
import shared.logic.ConcurrencyManager;
import client.threads.GameHelper;

public class MainClient extends Application {
    public static GameHelper game;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void main(String [] args) {
        // Create NetworkRmiClient singleton to setup networking and RMI
        NetworkRmiClient.setInstance();

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