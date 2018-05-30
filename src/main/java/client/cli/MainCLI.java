package client.cli;

import client.MiddlewareClient;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import shared.Logger;
import shared.TransferObjects.GameManagerT;

import java.util.ArrayList;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainCLI {
    private final Scanner readInput = new Scanner(System.in);

    public MainCLI() {
        super();
    }

    public void launch() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        AnsiConsole.out().print(ansi().eraseScreen(Ansi.Erase.ALL));
        AnsiConsole.out().println(ansi().fgBrightRed().a("Press enter to connect... ").fgDefault());
        readInput.nextLine();

        String resp = MiddlewareClient.getInstance().startGame();
        AnsiConsole.out().println(ansi().fgBrightRed().a("Server response: ").fgDefault().a(resp));
        if (resp.equals("Connections successful. Please wait for other players to connect")) {
            waitingRoom();
        }
    }

    private void waitingRoom() {
        //TODO Implement waiting
    }

    public void chooseWindow(ArrayList<Integer> windows) {
        AnsiConsole.out().println(ansi().fgBrightRed().a("Please select a window to play with, among the following:")
                .fgDefault());
        for (Integer w : windows) {
            AnsiConsole.out().print(w.toString() + "\t");
        }
        AnsiConsole.out().println();
        MiddlewareClient.getInstance().chooseWindowBack(readInput.nextInt());
    }

    public void startGameViewForced() {
        AnsiConsole.out().println(ansi().fgBrightRed().a("Start of game forced by the server").fgDefault());
    }

    public void updateView(GameManagerT gm) {
        AnsiConsole.out().println(ansi().fgBrightGreen().a("I was updated with " + gm.toString()));
    }
}