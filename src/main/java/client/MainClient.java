package client;

import client.cli.MainCLI;
import client.gui.StartGameController;
import client.gui.ChooseWindowController;
import client.gui.WaitingRoomController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fusesource.jansi.AnsiConsole;
import shared.Logger;
import shared.network.MethodConnectionException;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;

import static org.fusesource.jansi.Ansi.*;

/**
 * <p>This class will act as the client's launcher, allowing the user to choose the connection and the interface type,
 * before starting the game; moreover, this class will act as a container for the selected settings, and it will retrieve
 * the user's system UUID</p>
 */
public class MainClient extends Application {
    private static String uuid = null;
    private static boolean isPrompt = false;

    private static ChooseWindowController chooseWindowController;
    private static StartGameController startGameController;
    private static WaitingRoomController waitingRoomController;
    private static MainCLI cliController;
    private static ArrayList<Integer> chosenCards;

    /**
     * Getter for a user setting
     * @return {@code true} whether ths user wants to play through CLI
     */
    public static boolean isPrompt() {
        return isPrompt;
    }

    /**
     * Getter for a CLI controller
     * @return cliController the controller to handle the CLI
     */
    public static MainCLI getCliController() {
        return MainClient.cliController;
    }

    /**
     * Getter for a user setting
     * @return {@code String} containing the UUID from the user's OS
     */
    public static String getUuid() {
        return MainClient.uuid;
    }

    /**
     * Setter for a user setting
     * @param chosenCards contains the objective cards available to the players
     */
    public static void setChosenCards(ArrayList<Integer> chosenCards){
        MainClient.chosenCards = chosenCards;
    }

    /**
     * Getter for a user setting
     * @return chosenCards contains the objective cards available to the players
     */
    public static ArrayList<Integer> getChosenCards(){
        return chosenCards;
    }

    /**
     * Setter for a GUI controller
     * @param waitingRoomController the controller to handle the WaitingRoom scene
     */
    public static void setWaitingRoomController(WaitingRoomController waitingRoomController){
        MainClient.waitingRoomController = waitingRoomController;
    }

    /**
     * Getter for a GUI controller
     * @return waitingRoomController the controller to handle the WaitingRoom scene
     */
    public static WaitingRoomController getWaitingRoomController(){
        return  waitingRoomController;
    }

    /**
     * Setter for a GUI controller
     * @param chooseWindowController the controller to handle the ChooseWindow scene
     */
    public static void setChooseWindowController(ChooseWindowController chooseWindowController){
        MainClient.chooseWindowController = chooseWindowController;
    }

    /**
     * Getter for a GUI controller
     * @return chooseWindowController the controller to handle the ChooseWindow scene
     */
    public static ChooseWindowController getChooseWindowControllerController(){
        return chooseWindowController;
    }

    /**
     * Setter for a GUI controller
     * @param startGameController the controller to handle the StartGame scene
     */
    public static void setStartGameController(StartGameController startGameController){
        MainClient.startGameController = startGameController;
    }

    /**
     * Getter for a GUI controller
     * @return startGameController the controller to handle the StartGame scene
     */
    public static StartGameController getStartGameController(){
        return startGameController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void main(String[] args) {
        String serverIp = "";
        if (args.length > 0) {
            serverIp = args[0];
        }
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        AnsiConsole.out().println();
        AnsiConsole.out().println(ansi().fgYellow().a("Sagrada").fgBrightBlue().a(" board game\n").fgDefault());
        uuid = readUuid();
        Logger.log("UUID: " + uuid);

        AnsiConsole.out().println(ansi().fgBrightRed().a("Choose the connection type ('Rmi' | 'Socket')").fgDefault());
        Scanner scan = new Scanner(System.in);
        String connection = scan.nextLine().toLowerCase();
        while(!connection.equals("rmi") && !connection.equals("socket") ){
            AnsiConsole.out().println(ansi().fgBrightRed().a("Please provide a valid choice").fgDefault());
            connection = scan.nextLine();
        }
        if (connection.equals("rmi")){
            // Create new NetworkRmi client locating registry with default server IP and default object port
            ProxyClient.setConnection(new NetworkRmi(serverIp));
        } else if (connection.equals("socket")){
            try {
                // Create new NetworkSocket client connecting to default server IP and OS given port
                ProxyClient.setConnection(new NetworkSocket(serverIp, 0));
            } catch (MethodConnectionException mce) {
                Logger.strace(mce);
            }
        }

        AnsiConsole.out().println(ansi().fgBrightRed().a("Choose the input interface ('GUI' | 'CMD')").fgDefault());
        String inputMode = scan.nextLine().toLowerCase();
        while(!inputMode.equals("cmd") && !inputMode.equals("gui") ){
            AnsiConsole.out().println(ansi().fgBrightRed().a("Please provide a valid choice").fgDefault());
            inputMode = scan.nextLine();
        }
        if (inputMode.equals("cmd")){
            isPrompt = true;
            cliController = new MainCLI();
            cliController.launch();
        }
        else if (inputMode.equals("gui")){
            launch(args);
        }
        System.exit(0);
    }

    private static String readUuid() {
        Process process;
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                String cmd = "wmic csproduct get UUID";
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null && i < 3) {
                    uuid = line;
                    i++;
                }
                uuid = uuid.substring(0,uuid.length()-1); // Remove blank
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                AnsiConsole.out().println(ansi().fgBrightRed().a("Please provide your root password to read the UUID").fgDefault());
                Console cnsl = System.console();
                char[] pwd = cnsl.readPassword("Password: ");

                String pass = new String(pwd);
                StringBuilder output = new StringBuilder();
                String[] cmd = {"/bin/sh", "-c", "echo " + pass + " | sudo -S cat /sys/class/dmi/id/product_uuid"};

                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append('\n');
                }
                uuid = output.toString();
            } else if (os.contains("mac")) {
                StringBuilder output = new StringBuilder();
                String[] cmd = {"ioreg -l | awk '/product-name/ { split($0, line, \"\\\"\"); printf(\"%s\\n\", line[4])}'"};
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append('\n');
                }
                uuid = output.toString();
            }
        } catch (Exception e) {
            Logger.strace(e);
        }
        uuid = uuid.substring(0,uuid.length()-1); // Remove newline
        return uuid;
    }
}

