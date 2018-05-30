package client.cli;

import client.MiddlewareClient;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import shared.Cell;
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
                    switch (s) {
                        case "dice":
                            AnsiConsole.out().print(ansi().fgBrightRed()
                                    .a("Input the number of the dice you want to pick: ").fgDefault());
                            break;
                        case "card":
                            AnsiConsole.out().print(ansi().fgBrightRed()
                                    .a("Input the number of the tool card you want to use: ").fgDefault());
                            break;
                        case "end":
                            AnsiConsole.out().println(ansi().fgBrightRed()
                                    .a("You ended your turn: wait for your opponents").fgDefault());
                            MiddlewareClient.getInstance().endTurn();
                            break;
                        default:
                            wrongCommand();
                            break;
                    }
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

    public void chooseWindow(ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
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