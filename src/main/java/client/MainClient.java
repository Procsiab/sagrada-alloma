package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.logic.ConcurrencyManager;
import client.threads.GameHelper;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.Console;

public class MainClient extends Application {
    public static GameHelper game; // Game resetta scelte nel caso di stronzate
    public static String pass;
    public static String uUID = null;
    private static Console cnsl;
    private static String connection;
    private static String interfaccia;
    private static boolean isPrompt;
    /*riferimenti alle finestre
    ...
    */


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
                while ((line = reader.readLine()) != null && i < 3) {
                    uUID = line;
                    i++;
                }
                i = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            //System.out.println(uUID);
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            System.out.println("UEIII MAIALE INSERISCI LA PASSWORD");
            cnsl = System.console();
            // read password into the char array
            char[] pwd = cnsl.readPassword("Password: ");

            // prints
            System.out.println("Password is: "+ new String(pwd));
            pass = new String(pwd);
            StringBuffer output = new StringBuffer();
            Process process;

            /*Scanner scanner = new Scanner(System.in);
            System.out.println("enter the password");
            pass = scanner.nextLine();
            */

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

        } else if (OS.indexOf("mac") > 0) {
            //throw away this shit

            StringBuffer output = new StringBuffer();
            Process process;

            Scanner scanner = new Scanner(System.in);
            System.out.println("enter the password");
            pass = scanner.nextLine();
            //TODO Let the user provide the password

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
                e.printStackTrace();
            }

            uUID = output.toString();

        }

        System.out.println("wei");

        System.out.println("Scegli connessione porcel,lino, anche se non te ne sbatte molto. 'Rmi' o 'Socket' ");

        Scanner inConnection = new Scanner(System.in);
        connection = inConnection.nextLine();
        while(!connection.equals("Rmi") && !connection.equals("Socket") ){
            System.out.println("Connessione scelta non valida, reinserire connessione");
            connection = inConnection.nextLine();}
        System.out.println("Connessione selezionata: " + connection);

        if (connection.equals("Rmi")){
            MiddlewareClient.setConnection(new NetworkRmi(""));
        }
        else if (connection.equals("Socket")){
            MiddlewareClient.setConnection(new NetworkSocket(""));
        }


        System.out.println("Uei pippo civati vuoi far una relazione grafica o da cmd? . 'CMD' o 'GUI' ");

        Scanner inInterface = new Scanner(System.in);
        interfaccia = inInterface.nextLine();
        while(!interfaccia.equals("CMD") && !interfaccia.equals("GUI") ){
            System.out.println("Interfaccia scelta non valida, reinserire connessione");
            interfaccia = inInterface.nextLine();}
        System.out.println("Interfaccia Selezionata: " + interfaccia);

        if (interfaccia.equals("CMD")){
            isPrompt= true ;
        }
        else if (interfaccia.equals("GUI")){
            launch(args);
        }

    }
}