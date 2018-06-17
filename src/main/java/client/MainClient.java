package client;

import client.cli.MainCLI;
import client.gui.LogInScreenController;
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

public class MainClient extends Application {
    public static String uuid = null;
    private static boolean isPrompt = false;
    private static Console cnsl;
    private static String connection;
    private static String interfaccia;

    public static LogInScreenController logInScreenController;
    public static ChooseWindowController chooseWindowController;
    public static StartGameController startGameController;
    public static WaitingRoomController waitingRoomController;
    public static MainCLI cliController;
    public static ArrayList<Integer> choosenCards;

    public static boolean isPrompt() {
        return isPrompt;
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
        // Use current seconds as UUID, allowing multiple connections from the same machine
        uuid = String.valueOf(LocalTime.now().getSecond());
        //uuid = getUuid();
        Logger.log("UUID: " + uuid);

        AnsiConsole.out().println(ansi().fgBrightRed().a("Choose the connection type ('Rmi' | 'Socket')").fgDefault());
        Scanner scan = new Scanner(System.in);
        connection = scan.nextLine().toLowerCase();
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
        interfaccia = scan.nextLine().toLowerCase();
        while(!interfaccia.equals("cmd") && !interfaccia.equals("gui") ){
            AnsiConsole.out().println(ansi().fgBrightRed().a("Please provide a valid choice").fgDefault());
            interfaccia = scan.nextLine();
        }
        if (interfaccia.equals("cmd")){
            isPrompt = true;
            cliController = new MainCLI();
            cliController.launch();
        }
        else if (interfaccia.equals("gui")){
            launch(args);
        }
        System.exit(0);
    }

    private static String getUuid() {
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
                cnsl = System.console();
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