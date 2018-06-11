package server;

import shared.Cell;
import shared.Logger;

import java.util.ArrayList;
import java.util.LinkedList;

public class MatchManager {
    private static final Integer MAX_ACTIVE_PLAYER_REFS = Config.maxActivePlayerRefs;
    private static LinkedList<String> q = new LinkedList<>();
    private static ArrayList<Window> windows = new ArrayList<>();
    private static final Object obj = new Object();
    private static final Object obj2 = new Object();
    private static MatchManager instance = new MatchManager();

    private MatchManager() {
        super();


        //building windows
        Cell[][] cells = new Cell[4][5];
        cells[0][0] = new Cell('y');
        cells[0][1] = new Cell('b');
        cells[0][2] = new Cell();
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(1);
        cells[1][0] = new Cell('g');
        cells[1][1] = new Cell();
        cells[1][2] = new Cell(5);
        cells[1][3] = new Cell();
        cells[1][4] = new Cell(4);
        cells[2][0] = new Cell(3);
        cells[2][1] = new Cell();
        cells[2][2] = new Cell('r');
        cells[2][3] = new Cell();
        cells[2][4] = new Cell('g');
        cells[3][0] = new Cell(2);
        cells[3][1] = new Cell();
        cells[3][2] = new Cell();
        cells[3][3] = new Cell('b');
        cells[3][4] = new Cell('y');
        windows.add(new Window(cells, "Window1", 4));

        cells = new Cell[4][5];
        cells[0][0] = new Cell('v');
        cells[0][1] = new Cell(6);
        cells[0][2] = new Cell();
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(3);
        cells[1][0] = new Cell(5);
        cells[1][1] = new Cell('v');
        cells[1][2] = new Cell(3);
        cells[1][3] = new Cell();
        cells[1][4] = new Cell();
        cells[2][0] = new Cell();
        cells[2][1] = new Cell(2);
        cells[2][2] = new Cell('v');
        cells[2][3] = new Cell(1);
        cells[2][4] = new Cell();
        cells[3][0] = new Cell();
        cells[3][1] = new Cell(1);
        cells[3][2] = new Cell(5);
        cells[3][3] = new Cell('v');
        cells[3][4] = new Cell(4);
        windows.add(new Window(cells, "Window2", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell(4);
        cells[0][2] = new Cell();
        cells[0][3] = new Cell('y');
        cells[0][4] = new Cell(6);
        cells[1][0] = new Cell('r');
        cells[1][1] = new Cell();
        cells[1][2] = new Cell(2);
        cells[1][3] = new Cell();
        cells[1][4] = new Cell();
        cells[2][0] = new Cell();
        cells[2][1] = new Cell();
        cells[2][2] = new Cell('r');
        cells[2][3] = new Cell('v');
        cells[2][4] = new Cell(1);
        cells[3][0] = new Cell('b');
        cells[3][1] = new Cell('y');
        cells[3][2] = new Cell();
        cells[3][3] = new Cell();
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window3", 3));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell();
        cells[0][2] = new Cell();
        cells[0][3] = new Cell('r');
        cells[0][4] = new Cell(5);
        cells[1][0] = new Cell();
        cells[1][1] = new Cell();
        cells[1][2] = new Cell('v');
        cells[1][3] = new Cell(4);
        cells[1][4] = new Cell('b');
        cells[2][0] = new Cell();
        cells[2][1] = new Cell('b');
        cells[2][2] = new Cell(3);
        cells[2][3] = new Cell('y');
        cells[2][4] = new Cell(6);
        cells[3][0] = new Cell('y');
        cells[3][1] = new Cell(2);
        cells[3][2] = new Cell('g');
        cells[3][3] = new Cell(1);
        cells[3][4] = new Cell('r');
        windows.add(new Window(cells, "Window4", 5));

        cells[0][0] = new Cell();
        cells[0][1] = new Cell();
        cells[0][2] = new Cell(1);
        cells[0][3] = new Cell();
        cells[0][4] = new Cell();
        cells[1][0] = new Cell(1);
        cells[1][1] = new Cell('g');
        cells[1][2] = new Cell(3);
        cells[1][3] = new Cell('b');
        cells[1][4] = new Cell(2);
        cells[2][0] = new Cell('b');
        cells[2][1] = new Cell(5);
        cells[2][2] = new Cell(4);
        cells[2][3] = new Cell(6);
        cells[2][4] = new Cell('g');
        cells[3][0] = new Cell();
        cells[3][1] = new Cell('b');
        cells[3][2] = new Cell(5);
        cells[3][3] = new Cell('g');
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window5", 6));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell(1);
        cells[0][2] = new Cell('g');
        cells[0][3] = new Cell('v');
        cells[0][4] = new Cell(4);
        cells[1][0] = new Cell(6);
        cells[1][1] = new Cell('v');
        cells[1][2] = new Cell(2);
        cells[1][3] = new Cell(5);
        cells[1][4] = new Cell('g');
        cells[2][0] = new Cell(1);
        cells[2][1] = new Cell('g');
        cells[2][2] = new Cell(5);
        cells[2][3] = new Cell(3);
        cells[2][4] = new Cell('v');
        cells[3][0] = new Cell();
        cells[3][1] = new Cell();
        cells[3][2] = new Cell();
        cells[3][3] = new Cell();
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window6", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(1);
        cells[0][1] = new Cell();
        cells[0][2] = new Cell(3);
        cells[0][3] = new Cell('b');
        cells[0][4] = new Cell();
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(2);
        cells[1][2] = new Cell('b');
        cells[1][3] = new Cell();
        cells[1][4] = new Cell();
        cells[2][0] = new Cell(6);
        cells[2][1] = new Cell('b');
        cells[2][2] = new Cell();
        cells[2][3] = new Cell(4);
        cells[2][4] = new Cell();
        cells[3][0] = new Cell('b');
        cells[3][1] = new Cell(5);
        cells[3][2] = new Cell(2);
        cells[3][3] = new Cell();
        cells[3][4] = new Cell(1);
        windows.add(new Window(cells, "Window7", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(6);
        cells[0][1] = new Cell('b');
        cells[0][2] = new Cell();
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(1);
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(5);
        cells[1][2] = new Cell('b');
        cells[1][3] = new Cell();
        cells[1][4] = new Cell();
        cells[2][0] = new Cell(4);
        cells[2][1] = new Cell('r');
        cells[2][2] = new Cell(2);
        cells[2][3] = new Cell('b');
        cells[2][4] = new Cell();
        cells[3][0] = new Cell('g');
        cells[3][1] = new Cell(6);
        cells[3][2] = new Cell('y');
        cells[3][3] = new Cell(3);
        cells[3][4] = new Cell('v');
        windows.add(new Window(cells, "Window8", 6));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell('b');
        cells[0][2] = new Cell(2);
        cells[0][3] = new Cell();
        cells[0][4] = new Cell('y');
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(4);
        cells[1][2] = new Cell();
        cells[1][3] = new Cell('r');
        cells[1][4] = new Cell();
        cells[2][0] = new Cell();
        cells[2][1] = new Cell();
        cells[2][2] = new Cell(5);
        cells[2][3] = new Cell('y');
        cells[2][4] = new Cell();
        cells[3][0] = new Cell('g');
        cells[3][1] = new Cell(3);
        cells[3][2] = new Cell();
        cells[3][3] = new Cell();
        cells[3][4] = new Cell('v');
        windows.add(new Window(cells, "Window9", 3));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(5);
        cells[0][1] = new Cell('v');
        cells[0][2] = new Cell();
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(5);
        cells[1][0] = new Cell('5');
        cells[1][1] = new Cell();
        cells[1][2] = new Cell('v');
        cells[1][3] = new Cell();
        cells[1][4] = new Cell();
        cells[2][0] = new Cell('r');
        cells[2][1] = new Cell(6);
        cells[2][2] = new Cell();
        cells[2][3] = new Cell('v');
        cells[2][4] = new Cell();
        cells[3][0] = new Cell('y');
        cells[3][1] = new Cell('r');
        cells[3][2] = new Cell(5);
        cells[3][3] = new Cell(4);
        cells[3][4] = new Cell(3);
        windows.add(new Window(cells, "Window10", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(5);
        cells[0][1] = new Cell('g');
        cells[0][2] = new Cell('b');
        cells[0][3] = new Cell('v');
        cells[0][4] = new Cell(2);
        cells[1][0] = new Cell('v');
        cells[1][1] = new Cell();
        cells[1][2] = new Cell();
        cells[1][3] = new Cell();
        cells[1][4] = new Cell('y');
        cells[2][0] = new Cell('y');
        cells[2][1] = new Cell();
        cells[2][2] = new Cell(6);
        cells[2][3] = new Cell('g');
        cells[2][4] = new Cell('v');
        cells[3][0] = new Cell(1);
        cells[3][1] = new Cell();
        cells[3][2] = new Cell();
        cells[3][3] = new Cell();
        cells[3][4] = new Cell(4);
        windows.add(new Window(cells, "Window11", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell('r');
        cells[0][1] = new Cell();
        cells[0][2] = new Cell('b');
        cells[0][3] = new Cell();
        cells[0][4] = new Cell('y');
        cells[1][0] = new Cell(4);
        cells[1][1] = new Cell('v');
        cells[1][2] = new Cell(3);
        cells[1][3] = new Cell('g');
        cells[1][4] = new Cell(2);
        cells[2][0] = new Cell();
        cells[2][1] = new Cell(1);
        cells[2][2] = new Cell();
        cells[2][3] = new Cell(5);
        cells[2][4] = new Cell();
        cells[3][0] = new Cell();
        cells[3][1] = new Cell();
        cells[3][2] = new Cell(6);
        cells[3][3] = new Cell();
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window12", 4));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(2);
        cells[0][1] = new Cell();
        cells[0][2] = new Cell(5);
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(1);
        cells[1][0] = new Cell('y');
        cells[1][1] = new Cell(6);
        cells[1][2] = new Cell('v');
        cells[1][3] = new Cell(2);
        cells[1][4] = new Cell('r');
        cells[2][0] = new Cell();
        cells[2][1] = new Cell('b');
        cells[2][2] = new Cell(4);
        cells[2][3] = new Cell('g');
        cells[2][4] = new Cell();
        cells[3][0] = new Cell();
        cells[3][1] = new Cell(3);
        cells[3][2] = new Cell();
        cells[3][3] = new Cell(5);
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window13", 6));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(4);
        cells[0][1] = new Cell();
        cells[0][2] = new Cell(2);
        cells[0][3] = new Cell(5);
        cells[0][4] = new Cell('g');
        cells[1][0] = new Cell();
        cells[1][1] = new Cell();
        cells[1][2] = new Cell(6);
        cells[1][3] = new Cell('g');
        cells[1][4] = new Cell(2);
        cells[2][0] = new Cell();
        cells[2][1] = new Cell(3);
        cells[2][2] = new Cell('g');
        cells[2][3] = new Cell(4);
        cells[2][4] = new Cell();
        cells[3][0] = new Cell(5);
        cells[3][1] = new Cell('g');
        cells[3][2] = new Cell(1);
        cells[3][3] = new Cell();
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window14", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(3);
        cells[0][1] = new Cell(4);
        cells[0][2] = new Cell(1);
        cells[0][3] = new Cell(5);
        cells[0][4] = new Cell();
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(6);
        cells[1][2] = new Cell(2);
        cells[1][3] = new Cell();
        cells[1][4] = new Cell('y');
        cells[2][0] = new Cell();
        cells[2][1] = new Cell();
        cells[2][2] = new Cell();
        cells[2][3] = new Cell('y');
        cells[2][4] = new Cell('r');
        cells[3][0] = new Cell(5);
        cells[3][1] = new Cell();
        cells[3][2] = new Cell('y');
        cells[3][3] = new Cell('r');
        cells[3][4] = new Cell(6);
        windows.add(new Window(cells, "Window15", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(1);
        cells[0][1] = new Cell('v');
        cells[0][2] = new Cell('y');
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(4);
        cells[1][0] = new Cell('v');
        cells[1][1] = new Cell('y');
        cells[1][2] = new Cell();
        cells[1][3] = new Cell();
        cells[1][4] = new Cell(6);
        cells[2][0] = new Cell('y');
        cells[2][1] = new Cell();
        cells[2][2] = new Cell();
        cells[2][3] = new Cell(5);
        cells[2][4] = new Cell(3);
        cells[3][0] = new Cell();
        cells[3][1] = new Cell(5);
        cells[3][2] = new Cell(4);
        cells[3][3] = new Cell(2);
        cells[3][4] = new Cell(1);
        windows.add(new Window(cells, "Window16", 6));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell();
        cells[0][2] = new Cell(6);
        cells[0][3] = new Cell();
        cells[0][4] = new Cell();
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(5);
        cells[1][2] = new Cell('b');
        cells[1][3] = new Cell(4);
        cells[1][4] = new Cell();
        cells[2][0] = new Cell(3);
        cells[2][1] = new Cell('g');
        cells[2][2] = new Cell('y');
        cells[2][3] = new Cell('v');
        cells[2][4] = new Cell(2);
        cells[3][0] = new Cell(1);
        cells[3][1] = new Cell(4);
        cells[3][2] = new Cell('r');
        cells[3][3] = new Cell(5);
        cells[3][4] = new Cell(3);
        windows.add(new Window(cells, "Window17", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell('b');
        cells[0][1] = new Cell(6);
        cells[0][2] = new Cell();
        cells[0][3] = new Cell();
        cells[0][4] = new Cell('y');
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(3);
        cells[1][2] = new Cell('b');
        cells[1][3] = new Cell();
        cells[1][4] = new Cell();
        cells[2][0] = new Cell();
        cells[2][1] = new Cell(5);
        cells[2][2] = new Cell(6);
        cells[2][3] = new Cell(2);
        cells[2][4] = new Cell();
        cells[3][0] = new Cell();
        cells[3][1] = new Cell(4);
        cells[3][2] = new Cell();
        cells[3][3] = new Cell(1);
        cells[3][4] = new Cell('g');
        windows.add(new Window(cells, "Window18", 3));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell('b');
        cells[0][2] = new Cell('r');
        cells[0][3] = new Cell();
        cells[0][4] = new Cell();
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(4);
        cells[1][2] = new Cell(5);
        cells[1][3] = new Cell();
        cells[1][4] = new Cell('b');
        cells[2][0] = new Cell('b');
        cells[2][1] = new Cell(2);
        cells[2][2] = new Cell();
        cells[2][3] = new Cell('r');
        cells[2][4] = new Cell(5);
        cells[3][0] = new Cell(6);
        cells[3][1] = new Cell('r');
        cells[3][2] = new Cell(3);
        cells[3][3] = new Cell(1);
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window19", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell();
        cells[0][2] = new Cell('r');
        cells[0][3] = new Cell(5);
        cells[0][4] = new Cell();
        cells[1][0] = new Cell('v');
        cells[1][1] = new Cell(4);
        cells[1][2] = new Cell();
        cells[1][3] = new Cell('g');
        cells[1][4] = new Cell(3);
        cells[2][0] = new Cell(6);
        cells[2][1] = new Cell();
        cells[2][2] = new Cell();
        cells[2][3] = new Cell('b');
        cells[2][4] = new Cell();
        cells[3][0] = new Cell();
        cells[3][1] = new Cell('y');
        cells[3][2] = new Cell(2);
        cells[3][3] = new Cell();
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window20", 300));

        cells = new Cell[4][5];
        cells[0][0] = new Cell();
        cells[0][1] = new Cell();
        cells[0][2] = new Cell('g');
        cells[0][3] = new Cell();
        cells[0][4] = new Cell();
        cells[1][0] = new Cell(2);
        cells[1][1] = new Cell('y');
        cells[1][2] = new Cell(5);
        cells[1][3] = new Cell('b');
        cells[1][4] = new Cell(1);
        cells[2][0] = new Cell();
        cells[2][1] = new Cell('r');
        cells[2][2] = new Cell(3);
        cells[2][3] = new Cell('v');
        cells[2][4] = new Cell();
        cells[3][0] = new Cell(1);
        cells[3][1] = new Cell();
        cells[3][2] = new Cell(6);
        cells[3][3] = new Cell();
        cells[3][4] = new Cell(4);
        windows.add(new Window(cells, "Window21", 4));

        cells = new Cell[4][5];
        cells[0][0] = new Cell('y');
        cells[0][1] = new Cell();
        cells[0][2] = new Cell(2);
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(6);
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(4);
        cells[1][2] = new Cell();
        cells[1][3] = new Cell(5);
        cells[1][4] = new Cell('y');
        cells[2][0] = new Cell();
        cells[2][1] = new Cell();
        cells[2][2] = new Cell();
        cells[2][3] = new Cell('y');
        cells[2][4] = new Cell(5);
        cells[3][0] = new Cell(1);
        cells[3][1] = new Cell(2);
        cells[3][2] = new Cell('y');
        cells[3][3] = new Cell(3);
        cells[3][4] = new Cell();
        windows.add(new Window(cells, "Window22", 5));

        cells = new Cell[4][5];
        cells[0][0] = new Cell('y');
        cells[0][1] = new Cell();
        cells[0][2] = new Cell(6);
        cells[0][3] = new Cell();
        cells[0][4] = new Cell();
        cells[1][0] = new Cell();
        cells[1][1] = new Cell(1);
        cells[1][2] = new Cell(5);
        cells[1][3] = new Cell();
        cells[1][4] = new Cell(2);
        cells[2][0] = new Cell(3);
        cells[2][1] = new Cell('y');
        cells[2][2] = new Cell('r');
        cells[2][3] = new Cell('v');
        cells[2][4] = new Cell();
        cells[3][0] = new Cell();
        cells[3][1] = new Cell();
        cells[3][2] = new Cell(4);
        cells[3][3] = new Cell(3);
        cells[3][4] = new Cell('r');
        windows.add(new Window(cells, "Window23", 4));

        cells = new Cell[4][5];
        cells[0][0] = new Cell(1);
        cells[0][1] = new Cell('r');
        cells[0][2] = new Cell(3);
        cells[0][3] = new Cell();
        cells[0][4] = new Cell(6);
        cells[1][0] = new Cell(5);
        cells[1][1] = new Cell(4);
        cells[1][2] = new Cell('r');
        cells[1][3] = new Cell(2);
        cells[1][4] = new Cell();
        cells[2][0] = new Cell();
        cells[2][1] = new Cell();
        cells[2][2] = new Cell(5);
        cells[2][3] = new Cell('r');
        cells[2][4] = new Cell(1);
        cells[3][0] = new Cell();
        cells[3][1] = new Cell();
        cells[3][2] = new Cell();
        cells[3][3] = new Cell(3);
        cells[3][4] = new Cell('r');
        windows.add(new Window(cells, "Window24", 5));
    }

    public static MatchManager getInstance() {
        return instance;
    }

    public static synchronized String startGame(String uUID, String nickName, String ip, Integer port, boolean isSocket) {

        if (SReferences.contains(uUID)) {
            Logger.log("Player: " + uUID + " has connection refused: already playing.");
            if (SReferences.getIsSocketRef(uUID) != isSocket)
                SReferences.addIsSocketRef(uUID, isSocket);
            return "You already playing! Hold on while the server calls you again";
        }

        if(nickName == null||nickName.equals(""))
            return "Please enter a valid NickName";

        if (SReferences.getActivePlayer().equals(MAX_ACTIVE_PLAYER_REFS)) {
            Logger.log("Player: " + uUID + " has connection refused: too many players.");
            return "Too many players connected. Please try again later. Sorry for that.";
        }

        synchronized (obj2) {
            if (!SReferences.checkNickNameRef(nickName,q))
                return "NickName is not available.";
        }

        Logger.log("Player: " + uUID + " has connection accepted.");

        SReferences.addUuidRefEnhanced(uUID);
        SReferences.addIpRef(uUID, ip);
        SReferences.addPortRef(uUID, port);
        SReferences.addIsSocketRef(uUID, isSocket);
        SReferences.addNickNameRef(uUID, nickName);

        synchronized (obj2) {
            q.addLast(uUID);
            obj2.notifyAll();
        }

        return "Connections successful. Please wait for other players to connect";
    }

    @Deprecated
    public static synchronized String startGameOld(String uUID, String ip, Integer port, boolean isSocket) {

        if (SReferences.contains(uUID)) {
            Logger.log("Player: " + uUID + " has connection refused: already playing.");
            if (SReferences.getIsSocketRef(uUID) != isSocket)
                SReferences.addIsSocketRef(uUID, isSocket);
            return "You already playing! Hold on while the server calls you again";
        }

        if (SReferences.getActivePlayer().equals(MAX_ACTIVE_PLAYER_REFS)) {
            Logger.log("Player: " + uUID + " has connection refused: too many players.");
            return "Too many players connected. Please try again later. Sorry for that.";
        }

        Logger.log("Player: " + uUID + " has connection accepted.");

        SReferences.addUuidRefEnhanced(uUID);
        SReferences.addIpRef(uUID, ip);
        SReferences.addPortRef(uUID, port);
        SReferences.addIsSocketRef(uUID, isSocket);

        synchronized (obj2) {
            q.addLast(uUID);
            obj2.notifyAll();
        }

        return "Connections successful. Please wait for other players to connect";
    }

    public static synchronized boolean exitGame1(String uUID) {
        boolean exit;
        synchronized (obj2) {
            exit = q.remove(uUID);
        }
        if (exit) {
            Logger.log("Player: " + uUID + " leaved platform before game started. Bye.");
            SReferences.removeRef(uUID);
            return true;
        }
        return false;
    }

    public static LinkedList<String> getQ() {
        return q;
    }

    public static ArrayList<Window> getWindows() {
        return windows;
    }

    public static Object getObj() {
        return obj;
    }

    public static Object getObj2() {
        return obj2;
    }

}