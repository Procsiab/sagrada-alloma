package client;

import client.network.NetworkClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class MainClient extends Application {

    public MainClient(String[] args) {
        super();
        //launch(args);
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
        try {
            // Create an instance of NetworkClient, which will have the role of client's interface
            NetworkClient.setInstance();
            // Call a method on the server throughout local interface
            NetworkClient.getInstance().connect(2);
        } catch (Exception e) { // Better exception handling
            e.printStackTrace();
        }
        // Close connection on command
        System.out.println("Send 'exit' command to teardown...");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        System.exit(0);
    }
}