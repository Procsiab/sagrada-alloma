package client;

/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;*/
import shared.Logger;
import shared.logic.ConcurrencyManager;
import client.threads.GameHelper;

import java.util.Arrays;

public class MainClient /*extends Application*/ {
    public static GameHelper game;

    public MainClient(String[] args) {
        super();
    }

    /*@Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }*/

    public static void main(String [] args) {
        MainClient.game = new GameHelper();
        ConcurrencyManager.submit(game);
        //launch(args);
        try {
            Thread.sleep(2000000000);
        } catch (InterruptedException ie){
            Logger.log("Thread sleep was interrupted!");
            Logger.log("Stack trace:\n" + Arrays.toString(ie.getStackTrace()));
            Thread.currentThread().interrupt(); //Proper handling of InterruptedException
        }
        // Close connection when window closes
        /*System.out.println("Send 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }*/
        System.exit(0);
    }
}