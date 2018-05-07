package client;

import client.gui.LogInScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.threads.GameHelper;
import shared.Logger;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.Console;

public class MainClient extends Application {
    public static GameHelper game; // Game resetta scelte nel caso di stronzate
    public static String pass;
    public static String uuid = null;
    private static Console cnsl;
    private static String connection;
    private static String interfaccia;
    private static boolean isPrompt;

    public static LogInScreenController logInScreenController;

    /*
    dice dado1
    dice dado2
    position posizione 1
    ...
    */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Obtain client UUID
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            Process process;
            String cmd = "wmic csproduct get UUID";
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null && i < 3) {
                    uuid = line;
                    i++;
                }

                uuid = uuid.substring(0,uuid.length()-2);

            } catch (Exception e) {
                Logger.strace(e);
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            /*System.out.println("UEIII MAIALE INSERISCI LA PASSWORD");
            cnsl = System.console();
            // read password into the char array
            char[] pwd = cnsl.readPassword("Password: ");
            // prints
            System.out.println("Password is: "+ new String(pwd));
            pass = new String(pwd);
            StringBuffer output = new StringBuffer();
            Process process;
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
            uuid = output.toString();
            //System.out.println(uuid);*/
            uuid = "0123456789";
        } else if (os.indexOf("mac") > 0) {
            StringBuffer output = new StringBuffer();
            Process process;
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter the password");
            pass = scanner.nextLine();
            //https://www.infoworld.com/article/3029204/macs/10-essential-os-x-command-line-tips-for-power-users.html
            String[] cmd = {};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            } catch (Exception e) {
                Logger.strace(e);
            }
            uuid = output.toString();
        }

        Logger.log("~ Choose the connection type ('Rmi' | 'Socket') ~");
        Scanner inConnection = new Scanner(System.in);
        connection = inConnection.nextLine();

        while(!connection.equals("Rmi") && !connection.equals("Socket") ){
            Logger.log("Please provide a valid choice");
            connection = inConnection.nextLine();
        }
        if (connection.equals("Rmi")){
            MiddlewareClient.setConnection(new NetworkRmi("", 0));
        }
        else if (connection.equals("Socket")){
            MiddlewareClient.setConnection(new NetworkSocket("", 0));
        }

        Logger.log("~ Choose the input interface ('GUI' | 'CMD') ~");
        Scanner inInterface = new Scanner(System.in);
        interfaccia = inInterface.nextLine();
        while(!interfaccia.equals("CMD") && !interfaccia.equals("GUI") ){
            Logger.log("Please provide a valid choice");
            interfaccia = inInterface.nextLine();
        }
        if (interfaccia.equals("CMD")){
            isPrompt= true ;
        }
        else if (interfaccia.equals("GUI")){
            launch(args);
        }
    }
}