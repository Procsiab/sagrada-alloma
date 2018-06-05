package client.cli;

import client.MiddlewareClient;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import shared.Cell;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.TransferObjects.ToolCT;

import java.util.ArrayList;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainCLI {
    private final Scanner readInput = new Scanner(System.in);
    private Integer functionId = 0;
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
            if (s.equals("kill")) {
                stop = true;
            }
            switch (functionId) {
                case 1: // chooseWindow()
                    int i = Integer.parseInt(s);
                    if (windows.contains(i)) {
                        MiddlewareClient.getInstance().chooseWindowBack(i);
                    } else {
                        wrongCommand();
                    }
                    break;
                case 2: // enable()
                    switch (s) {
                        case "dice":
                            AnsiConsole.out().print(ansi().fgBrightRed()
                                    .a("Input the number of the dice you want to pick: ").fgDefault());
                            i = readInput.nextInt();
                            if (this.gm.pool.size() < i) {
                                wrongCommand();
                            } else {
                                placeDice(i - 1);
                            }
                            s = "#";
                            break;
                        case "card":
                            AnsiConsole.out().print(ansi().fgBrightRed()
                                    .a("Input the number of the tool card you want to use: ").fgDefault());
                            i = readInput.nextInt();
                            if (this.gm.toolCards.size() < i) {
                                wrongCommand();
                                break;
                            }
                            useToolC(i - 1);
                            s = "#";
                            break;
                        case "end":
                            MiddlewareClient.getInstance().endTurn();
                            s = "#";
                            break;
                        case "\n":
                        case "":
                            break;
                        default:
                            wrongCommand();
                            break;
                    }
                    break;
                case 3: // method()
                    break;
                default: // functionId = 0
                    break;
            }
        } while (!stop);
    }

    private void wrongCommand() {
        AnsiConsole.out().println(ansi().fgBrightRed().a("[ERROR] ").fgBrightYellow()
                .a("Wrong input, check for typos!").fgDefault());
    }

    private void placeDice(Integer index) {
        AnsiConsole.out().print(ansi().fgBrightRed()
                .a("Enter row and column position of the cell you would like to place it, separated by a space: ").fgDefault());
        Integer r, c;
        r = readInput.nextInt();
        c = readInput.nextInt();
        Position p = new Position(r, c);
        if (MiddlewareClient.getInstance().placeDice(index, p)) {
            MiddlewareClient.getInstance().updateViewFromC();
        } else {
            AnsiConsole.out().println(ansi().fgBrightGreen().a("[INFO] ").fgBrightYellow()
                    .a("Selected dice could not be placed in specified position!").fgDefault());
        }
    }

    private void useToolC(Integer index) {
        ToolCT c = this.gm.toolCards.get(index);
        Integer i2 = null, i3 = null, row, col, hgt;
        Position p1 = null, p2 = null, p3 = null, p4 = null;
        PositionR pr = null;
        switch (c.name) {
            case "ToolC1":
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Pick a dice from pool: ").fgDefault());
                i2 = readInput.nextInt();
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Increment or decrement that dice (1 | -1): ").fgDefault());
                i3 = readInput.nextInt();
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Choose a placement position on the window (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p1 = new Position(row, col);
                break;
            case "ToolC2":
            case "ToolC3":
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Starting position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p1 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Desired position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p2 = new Position(row, col);
                break;
            case "ToolC4":
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("First starting position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p1 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("First desired position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p2 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Second starting position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p3 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Second desired position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p4 = new Position(row, col);
                break;
            case "ToolC5":
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Pick a dice from pool: ").fgDefault());
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Choose a placement position on the window (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p1 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Pick a position on the round track (column height): ").fgDefault());
                col = readInput.nextInt();
                hgt = readInput.nextInt();
                pr = new PositionR(col, hgt);
                break;
            case "ToolC6":
            case "ToolC8":
            case "ToolC9":
            case "ToolC10":
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Pick a dice from pool: ").fgDefault());
                i2 = readInput.nextInt();
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Choose a placement position on the window (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p1 = new Position(row, col);
                break;
            case "ToolC11":
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Pick a dice from pool: ").fgDefault());
                i2 = readInput.nextInt();
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Choose a placement position on the window (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p1 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Choose value for selected dice: ").fgDefault());
                i3 = readInput.nextInt();
                break;
            case "ToolC12":
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("First starting position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p1 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("First desired position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p2 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Second starting position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p3 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Second desired position (row column): ").fgDefault());
                row = readInput.nextInt();
                col = readInput.nextInt();
                p4 = new Position(row, col);
                AnsiConsole.out().print(ansi().fgBrightRed()
                        .a("Pick a position on the round track (column height): ").fgDefault());
                col = readInput.nextInt();
                hgt = readInput.nextInt();
                pr = new PositionR(col, hgt);
                break;
            case "ToolC7":
            default:
                break;
        }
        if (MiddlewareClient.getInstance().useToolC(index, p1, p2, p3, p4, pr, i2, i3)) {
            MiddlewareClient.getInstance().updateViewFromC();
        } else {
            AnsiConsole.out().println(ansi().fgBrightGreen().a("[INFO] ").fgBrightYellow()
                    .a("Selected Tool Card could not be used with specified parameters!").fgDefault());
        }
    }

    public void updateView(GameManagerT gm) {
        if (functionId == 2) {
            AnsiConsole.out().println(ansi().fgBrightRed().a("Game status update from server:"));
            this.gm = gm;
            //TODO Print GameManager object
        }
    }

    public void chooseWindow(ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
        functionId = 1;
        AnsiConsole.out().println(ansi().fgBrightRed().a("Please select a window to play with, among the following:")
                .fgDefault());
        for (Integer w : windows) {
            AnsiConsole.out().print(w.toString() + "\t");
        }
        AnsiConsole.out().println();
        this.windows = windows;
    }

    public void startGameViewForced() {
        AnsiConsole.out().println(ansi().fgBrightRed().a("Start of game forced by the server").fgDefault());
    }

    public void aPrioriWin() {
        AnsiConsole.out().println(ansi().fgBrightBlue().a("[WIN] ").fgBrightYellow()
                .a("You won because other opponents left the game!").fgDefault());
    }

    public void enable() {
        functionId = 2;
        AnsiConsole.out().println(ansi().fgBrightGreen().a("[INFO] ").fgBrightYellow()
                .a("Your turn has now started").fgDefault());
        MiddlewareClient.getInstance().updateViewFromC();
    }

    public void shut() {
        functionId = 0;
        AnsiConsole.out().println(ansi().fgBrightGreen().a("[INFO] ").fgBrightYellow()
                .a("Your turn has ended: wait for you opponents").fgDefault());
    }

    public void printScore(Integer score) {
        AnsiConsole.out().println(ansi().fgBrightRed().a("Game has ended! Your score: ").fgDefault()
                .a(score.toString()));
    }

    public void setWinner() {
        AnsiConsole.out().println(ansi().fgBrightBlue().a("[WIN] ").fgBrightYellow()
                .a("You won the match, making the highest score!").fgDefault());
    }
}