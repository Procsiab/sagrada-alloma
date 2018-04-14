package client;

import shared.Logic.ConcurrencyManager;
import client.threads.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static Game game;

    public MainClient(String[] args) {
        super();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void printMessage(String s) {
        System.out.println(s);
    }

    public static void main(String [] args) {

        MainClient.game = new Game();
        ConcurrencyManager.submit(game);


        // Call a method on the server throughout local interface
        //launch(args);



        try {
            Thread.sleep(2000000000);
        } catch (InterruptedException e){
            e.printStackTrace();
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