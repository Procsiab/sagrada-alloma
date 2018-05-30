package client.cli;

import client.MiddlewareClient;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import shared.TransferObjects.GameManagerT;

import java.util.ArrayList;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainCLI {
    private final Scanner readInput = new Scanner(System.in);
    private Integer functionId;
    private ArrayList<Integer> windows;
    private GameManagerT gm;

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

        boolean stop = false;
        do {
            String s = readInput.nextLine();
            switch (functionId) {
                case 1: // chooseWindow()
                    int i = Integer.parseInt(s);
                    if (windows.contains(i)) {
                        MiddlewareClient.getInstance().chooseWindowBack(i);
                    } else {
                        wrongCommand();
                    }
                    break;
                case 2: // updateView()
                    break;
                default:
                    break;
            }
        } while (!stop);
    }

    private void wrongCommand() {
        AnsiConsole.out().println(ansi().fgBrightRed().a("[ERROR] ").fgBrightYellow()
                .a("Wrong input, check for typos!").fgDefault());
    }

    public void chooseWindow(ArrayList<Integer> windows) {
        AnsiConsole.out().println(ansi().fgBrightRed().a("Please select a window to play with, among the following:")
                .fgDefault());
        for (Integer w : windows) {
            AnsiConsole.out().print(w.toString() + "\t");
        }
        AnsiConsole.out().println();
        this.windows = windows;
        functionId = 1;
    }

    public void startGameViewForced() {
        AnsiConsole.out().println(ansi().fgBrightRed().a("Start of game forced by the server").fgDefault());
    }

    public void updateView(GameManagerT gm) {
        AnsiConsole.out().println(ansi().fgBrightRed().a("Game status update from server:"));
        this.gm = gm;
        functionId = 2;
    }
}