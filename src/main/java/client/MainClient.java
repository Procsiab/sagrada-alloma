package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.logic.ConcurrencyManager;
import client.threads.GameHelper;
import shared.network.rmi.NetworkRmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainClient extends Application {
    public static GameHelper game;
    public static String pass;
    public static String uUID = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void main(String [] args) {
        //uUID detection

        String OS = System.getProperty("os.name").toLowerCase();

        if (OS.indexOf("win") >= 0) {
            Process process;
            String cmd = "wmic csproduct get UUID";
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null && i<3) {
                    uUID = line;
                    i++;
                }
                i = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            //System.out.println(uUID);
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {

            StringBuffer output = new StringBuffer();
            Process process;
            pass = "secretpass"; //TODO Let the user provide the password
            String[] cmd = {"/bin/sh", "-c", "echo " + pass + " | sudo -S cat /sys/class/dmi/id/product_uuid"};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            uUID = output.toString();


            //System.out.println(uUID);

        } else if(OS.indexOf("mac")>0){
            //throw away this shit
        }

        /*MainClient.game = new GameHelper();
        ConcurrencyManager.submit(game);*/
        launch(args);
    }
}