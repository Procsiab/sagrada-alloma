package client.cli;

import client.MiddlewareClient;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import shared.*;
import shared.TransferObjects.GameManagerT;
import shared.TransferObjects.PlayerT;
import shared.TransferObjects.ToolCT;
import shared.TransferObjects.WindowT;

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
                        functionId = 0;
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
                            break;
                        case "end":
                            MiddlewareClient.getInstance().endTurn();
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
    private void printCell(Cell c) {
        if (c.color == null && c.value == null) { // Empty white cell
            AnsiConsole.out().print(ansi().fg(Ansi.Color.WHITE).bg(Ansi.Color.WHITE).a(' ')
                    .fgDefault().bgDefault());
        } else if (c.value != null && c.color == null) {
            AnsiConsole.out().print(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.WHITE).a(c.value)
                    .fgDefault().bgDefault());
        } else if (c.value == null) {
            switch (c.color) {
                case 'r':
                    AnsiConsole.out().print(ansi().bgRed());
                    break;
                case 'b':
                    AnsiConsole.out().print(ansi().bg(Ansi.Color.BLUE));
                    break;
                case 'g':
                    AnsiConsole.out().print(ansi().bgGreen());
                    break;
                case 'y':
                    AnsiConsole.out().print(ansi().bgYellow());
                    break;
                case 'v':
                    AnsiConsole.out().print(ansi().bgMagenta());
                    break;
                default:
                    break;
            }
            AnsiConsole.out().print(ansi().a(' ').bgDefault());
        }
    }

    private void printDice(Dice d, boolean inverted) {
        if (inverted) {
            AnsiConsole.out().print(ansi().fg(Ansi.Color.BLACK));
            switch (d.color) {
                case 'r':
                    AnsiConsole.out().print(ansi().bgRed());
                    break;
                case 'b':
                    AnsiConsole.out().print(ansi().bg(Ansi.Color.BLUE));
                    break;
                case 'g':
                    AnsiConsole.out().print(ansi().bgGreen());
                    break;
                case 'y':
                    AnsiConsole.out().print(ansi().bgYellow());
                    break;
                case 'v':
                    AnsiConsole.out().print(ansi().bgMagenta());
                    break;
                default:
                    AnsiConsole.out().print('?');
                    break;
            }
        } else {
            AnsiConsole.out().print(ansi().bg(Ansi.Color.BLACK));
            switch (d.color) {
                case 'r':
                    AnsiConsole.out().print(ansi().fgBrightRed());
                    break;
                case 'b':
                    AnsiConsole.out().print(ansi().fgBrightBlue());
                    break;
                case 'g':
                    AnsiConsole.out().print(ansi().fgBrightGreen());
                    break;
                case 'y':
                    AnsiConsole.out().print(ansi().fgBrightYellow());
                    break;
                case 'v':
                    AnsiConsole.out().print(ansi().fgBrightMagenta());
                    break;
                default:
                    AnsiConsole.out().print('?');
                    break;
            }
        }
        AnsiConsole.out().print(ansi().a(d.value).fgDefault().bgDefault());
    }

    private void printWindow(WindowT w, Overlay o) {
        final int COLS = 5;
        final int ROWS = 4;
        for (int i = 0; i < ROWS; i++) {
            AnsiConsole.out().print(' '); // Space before every row
            for (int j = 0; j < COLS; j++) {
                Cell c = w.cells[i][j];
                Dice d = o.getDicePositions()[i][j];
                if (d == null) { // In case no dice is placed
                    printCell(c);
                } else {
                    printDice(d, false);
                }
                AnsiConsole.out().print(' '); // Tab after every dice or cell
            }
            AnsiConsole.out().print('\n'); // New line after every row
        }
        AnsiConsole.out().print('\n'); // New line after the last column of the last row
    }

    private void printWindow(Cell[][] cells) {
        final int COLS = 5;
        final int ROWS = 4;
        for (int i = 0; i < ROWS; i++) {
            AnsiConsole.out().print(' '); // Space before every row
            for (int j = 0; j < COLS; j++) {
                printCell(cells[i][j]);
                AnsiConsole.out().print(' '); // Tab after every dice or cell
            }
            AnsiConsole.out().print('\n'); // New line after every row
        }
        AnsiConsole.out().print('\n'); // New line after the last column of the last row
    }

    private void printPoolAndRoundTrack(GameManagerT gm) {
        AnsiConsole.out().print(ansi().fgBrightRed().a("Dice pool: ").fgDefault());
        for (Dice d : gm.pool) {
            printDice(d, true);
            AnsiConsole.out().print(' ');
        }
        AnsiConsole.out().print(ansi().fgBrightRed().a("\nRound track:\n").fgDefault());
        AnsiConsole.out().println("coming soon...");
    }

    private void printPlayerInfo(PlayerT p) {
        AnsiConsole.out.println(ansi().fgBrightRed().a("Turn number: ").fgDefault().a(p.turno));
        AnsiConsole.out.println(ansi().fgBrightRed().a("Tokens: ").fgDefault().a(p.tokens));
    }

    public void updateView(GameManagerT gm) {
        if (functionId == 2) {
            AnsiConsole.out().print(ansi().eraseScreen(Ansi.Erase.ALL).cursor(0, 0));
            AnsiConsole.out().println(ansi().fgBrightRed().a("Game status update from server:"));
            this.gm = gm;
            // Obtain local player from GameManager
            PlayerT me = gm.vPlayers.get(gm.pos);
            gm.vPlayers.remove(me);
            // Print other's windows
            for (PlayerT p : gm.vPlayers) {
                printWindow(p.window, p.overlay);
            }
            // Print local player's window
            AnsiConsole.out.println("My window:");
            printWindow(me.window, me.overlay);
            // Print other info
            printPoolAndRoundTrack(this.gm);
            printPlayerInfo(me);
        }
    }

    public void chooseWindow(ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
        functionId = 1;
        AnsiConsole.out().println(ansi().fgBrightRed().a("Please select a window to play with, among the following:")
                .fgDefault());
        for (int i = 0; i < windows.size(); i ++) {
            AnsiConsole.out().println("Window " + windows.get(i).toString());
            printWindow(matrices.get(i));
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