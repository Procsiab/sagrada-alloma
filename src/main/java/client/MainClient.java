package client;

import client.network.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.SharedNetworkClient;
import shared.SharedNetworkServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class MainClient extends Application {


    public MainClient(String[] args) {
        super();
        try {
            this.clientIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        Scene logIn = new Scene(root);
        primaryStage.setScene(logIn);
        primaryStage.show();
    }

    public static void printMessage(String s) {
        System.out.println(s);
    }

    public static void main(String [] args) throws NotBoundException, RemoteException {

        /*
        // Look for the RMI registry on specific server port
        Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_IP, RMI_PORT);
        // Get a reference to the remote instance of ServerP2P.Network, through SharedNetworkServer interface
        SharedNetworkServer netIface = (SharedNetworkServer) rmiRegistry.lookup(RMI_IFACE_NAME);
        System.out.println("Connecting...");

        // Create instance of client from its shared interface
        SharedNetworkClient myClient = new MainClient(args);
        // Inform the registry about symbolic server name
        System.setProperty("java.rmi.server.hostname", myClient.getClientIp());
        // Setup permissive security policy - yay haxorz come in
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        // Export the object listener on anonymous port
        UnicastRemoteObject.exportObject(myClient,0);
        // Call method on remote object passing the local reference
        netIface.connect(myClient);*/

        // Close connection on command
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("exit")) {
            //
        }
        System.exit(0);
    }
}
