package client.cli;

import client.ProxyClient;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import shared.*;
import shared.TransferObjects.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainCLI {
    private final Scanner readInput = new Scanner(System.in);
    private Integer functionId = -1;
    private ArrayList<Integer> windows;
    private GameManagerT gm;
    private PlayerT me = null;
    private boolean runForever = true;

    public MainCLI() {
        super();
    }

    public void launch() {
        // Start JANSI console
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        AnsiConsole.out().print(ansi().eraseScreen(Ansi.Erase.ALL).cursor(0, 0));
        String resp;
        // Enter a nick name and try to connect on the server
        do {
            AnsiConsole.out().print(ansi().fgBrightRed().a("Enter your nickname before connecting: ").fgDefault());
            String nick = readInput.nextLine();
            resp = ProxyClient.getInstance().startGame(nick);
            AnsiConsole.out().println(ansi().fgBrightRed().a("Server response: ").fgDefault().a(resp));
        } while (resp == null || resp.equals("NickName is not available.") ||
                resp.equals("Please enter a valid NickName") || resp.equals("Connection error"));
        // Main command status loop
        do {
            try {
                if (readInput.hasNextLine()) { // get user input
                    String s = readInput.nextLine();
                    if (s.equals("exit")) { // exit command
                        runForever = false;
                        if (functionId == -1) {
                            ProxyClient.getInstance().exitGame1();
                        } else {
                            ProxyClient.getInstance().exitGame2();
                        }
                        AnsiConsole.out().println(ansi().fgBrightRed().a("[EXIT] ").fgBrightYellow()
                                .a("You left the game").fgDefault());
                    } else { // other commands
                        useCommand(s);
                    }
                }
            } catch (InputMismatchException ime) {
                wrongCommand("command format not recognized!");
            } catch (NumberFormatException nfe) {
                wrongCommand("you should enter a valid integer number!");
            } catch (NullPointerException npe) {
                wrongCommand("server sent invalid data: check your connection");
            }
        } while (runForever);
    }

    private void useCommand(String s) {
        switch (functionId) {
            case 1: // chooseWindow()
                int i = Integer.parseInt(s);
                if (windows.contains(i)) {
                    ProxyClient.getInstance().chooseWindowBack(i);
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
                        ProxyClient.getInstance().endTurn();
                        break;
                    case "\n":
                    case "":
                        break;
                    default:
                        wrongCommand();
                        break;
                }
                break;
            default: // functionId = 0
                break;
        }
    }

    private void wrongCommand() {
        AnsiConsole.out().println(ansi().fgBrightRed().a("[ERROR] ").fgBrightYellow()
                .a("Wrong input, check for typos!").fgDefault());
    }

    private void wrongCommand(String m) {
        AnsiConsole.out().println(ansi().fgBrightRed().a("[ERROR] ").fgBrightYellow()
                .a("Wrong input: " + m).fgDefault());
    }

    private void placeDice(Integer index) {
        AnsiConsole.out().print(ansi().fgBrightRed()
                .a("Enter row and column position of the cell you would like to place it, separated by a space: ").fgDefault());
        Integer r;
        Integer c;
        r = readInput.nextInt();
        c = readInput.nextInt();
        Position p = new Position(r - 1, c - 1);
        if (ProxyClient.getInstance().placeDice(index, p)) {
            ProxyClient.getInstance().updateViewFromC();
        } else {
            AnsiConsole.out().println(ansi().fgBrightGreen().a("[INFO] ").fgBrightYellow()
                    .a("Selected dice could not be placed in specified position!").fgDefault());
        }
    }

    private void useToolC(Integer index) {
        ToolCT c = this.gm.toolCards.get(index);
        Integer i2 = null;
        Integer i3 = null;
        Integer row;
        Integer col;
        Integer hgt;
        Position p1 = null;
        Position p2 = null;
        Position p3 = null;
        Position p4 = null;
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
        if (ProxyClient.getInstance().useToolC(index, p1, p2, p3, p4, pr, i2, i3)) {
            ProxyClient.getInstance().updateViewFromC();
        } else {
            AnsiConsole.out().println(ansi().fgBrightGreen().a("[INFO] ").fgBrightYellow()
                    .a("Selected Tool Card could not be used with specified parameters!").fgDefault());
        }
    }

    private void printCell(Cell c) {
        if (c == null || (c.getColor() == null && c.getValue() == null)) { // Empty white cell
            AnsiConsole.out().print(ansi().fg(Ansi.Color.WHITE).bg(Ansi.Color.WHITE).a(' ')
                    .fgDefault().bgDefault());
        } else if (c.getValue() != null && c.getColor() == null) { // Dice value constraint
            AnsiConsole.out().print(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.WHITE).a(c.getValue())
                    .fgDefault().bgDefault());
        } else if (c.getValue() == null) { // Dice color constraint
            switch (c.getColor()) {
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
        if (d == null) {
            AnsiConsole.out().print('_');
        } else {
            if (inverted) {
                AnsiConsole.out().print(ansi().fg(Ansi.Color.BLACK));
                switch (d.getColor()) {
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
                AnsiConsole.out().print(ansi().bg(Ansi.Color.BLACK).bold());
                switch (d.getColor()) {
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
            AnsiConsole.out().print(ansi().a(d.getValue()).fgDefault().bgDefault().boldOff());
        }
    }

    private void printWindow(WindowT w, Dice[][] o) {
        final int COLS = 5;
        final int ROWS = 4;
        for (int i = 0; i < ROWS; i++) {
            AnsiConsole.out().print(' '); // Space before every row
            for (int j = 0; j < COLS; j++) {
                Cell c = w.cells[i][j];
                Dice d = o[i][j];
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

    private void printRoundTrack(ArrayList<ArrayList<Dice>> rt) {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j < 9; j++) {
                Dice d = null;
                if (rt.get(j).size() > i) {
                    d = rt.get(j).get(i);
                }
                printDice(d, true);
                AnsiConsole.out().print("  ");
            }
            AnsiConsole.out().print('\n');
        }
    }

    private void printPoolAndRoundTrack(GameManagerT gm) {
        AnsiConsole.out().print(ansi().fgBrightRed().a("Dice pool: ").fgDefault());
        for (Dice d : gm.pool) {
            printDice(d, true);
            AnsiConsole.out().print(' ');
        }
        AnsiConsole.out().print(ansi().fgBrightRed().a("\nRound track:\n").fgDefault());
        printRoundTrack(gm.roundTrack);
    }

    private void printPlayerInfo(PlayerT p) {
        AnsiConsole.out.println(ansi().fgBrightRed().a("Turn number: ").fgDefault().a(Integer.toString(p.turno)));
        AnsiConsole.out.print(ansi().fgBrightRed().a("Private objective: ").fgDefault());
        printCell(new Cell(null, p.privateO));
        AnsiConsole.out().print('\n');
        AnsiConsole.out.println(ansi().fgBrightRed().a("Public objectives:").fgDefault());
        for (String s : gm.publicOCs) {
            AnsiConsole.out().println("\t" + s);
        }
        AnsiConsole.out.print(ansi().fgBrightRed().a("Tokens: ").fgDefault());
        if (p.tokens > 6) {
            AnsiConsole.out().println("∞");
        } else {
            AnsiConsole.out().println(p.tokens);
        }
        AnsiConsole.out.println(ansi().fgBrightRed().a("Tool cards:").fgDefault());
        for (ToolCT tc : gm.toolCards) {
            AnsiConsole.out().println("\t" + tc.name + " (" + tc.tokensRequired.toString() + ")");
        }
    }

    private void printConnectionInfo(PlayerT p) {
        final String BALL = "●";
        if (this.gm.offline.contains(p.nickName)) {
            AnsiConsole.out().print(ansi().fgBrightRed());
        } else if (this.gm.online.contains(p.nickName)) {
            AnsiConsole.out().print(ansi().fgBrightGreen());
        } else {
            AnsiConsole.out().print(ansi().fgBrightYellow());
        }
        AnsiConsole.out().print(ansi().a(BALL).fgDefault().a(' ').bold().a(p.nickName).boldOff());
    }

    /* Public functions, used by ProxyClient to notify the view */

    public void updateView(GameManagerT gm) {
        if (functionId == 2) {
            AnsiConsole.out().print(ansi().eraseScreen(Ansi.Erase.ALL).cursor(0, 0));
            AnsiConsole.out().println(ansi().fgBrightRed().a("Game status update from server:").fgDefault());
            this.gm = gm;
            // Obtain local player from GameManager
            me = gm.vPlayers.get(gm.pos);
            // Remove current player from the match 's player list
            gm.vPlayers.remove(me);
            // Print other's windows
            for (PlayerT p : gm.vPlayers) {
                printConnectionInfo(p);
                AnsiConsole.out().println("'s window");
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
        AnsiConsole.out().println(ansi().fgBrightGreen().a("\n[INFO] ").fgBrightYellow()
                .a("Your turn has now started").fgDefault());
        ProxyClient.getInstance().updateViewFromC();
    }

    public void shut() {
        functionId = 0;
        AnsiConsole.out().println(ansi().fgBrightGreen().a("\n[INFO] ").fgBrightYellow()
                .a("Your turn has ended: wait for you opponents").fgDefault());
    }

    public void printScore(ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) {
        // Stop the main loop
        runForever = false;
        AnsiConsole.out().println(ansi().fgBrightBlue().a("\nSCOREBOARD").fgDefault());
        if (scores == null || winner == null) {
            AnsiConsole.out().println(ansi().fgBrightBlue().a("\n[END] ").fgBrightYellow()
                    .a("The player " + nicks.get(0) + " won the match, being the only one who stayed online").fgDefault());
        } else {
            boolean youWon = false;
            // loop on the players list, in the opposite order (they are ordered with respect to the score, increasing)
            for (int i = 0; i < nicks.size(); i++) {
                // Print current player's nick in bold
                if (nicks.get(i).equals(me.nickName)) {
                    AnsiConsole.out().print(ansi().bold());
                    // If you have the winner flag set, set also youWon flag
                    if (winner.get(i)) {
                        youWon = true;
                        AnsiConsole.out().print(" ★");
                    }
                } else if (winner.get(i)) {// Every winner will have a star badge on the left
                    AnsiConsole.out().print(" ★");
                }
                // Print nick name and score, on a single line
                AnsiConsole.out().println(ansi().a("\t" + nicks.get(i)).fgBrightYellow()
                        .a("\t" + scores.get(i).toString()).fgDefault().boldOff());
            }
            if (youWon) {
                AnsiConsole.out().println(ansi().fgBrightBlue().a("\n[WIN] ").fgBrightYellow()
                        .a("You won the match, making the highest score!").fgDefault());
            }
        }
    }
}