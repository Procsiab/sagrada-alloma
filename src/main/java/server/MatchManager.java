package server;

import server.abstractsServer.PublicOC;
import server.abstractsServer.ToolC;
import server.abstractsServer.Window;
import shared.Logger;
import shared.abstractsShared.*;
import shared.cardsShared.privateOC.PrivateOC2;
import shared.cardsShared.privateOC.PrivateOC4;
import shared.cardsShared.privateOC.PrivateOC5;
import server.cardsServer.publicOC.*;
import server.cardsServer.toolC.*;
import shared.cardsShared.privateOC.PrivateOC1;
import server.cardsServer.windows.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatchManager {
    private static final Integer MAX_ACTIVE_PLAYER_REFS = 250; //config
    private static LinkedList<String> q = new LinkedList<>();
    private ArrayList<String> clients = new ArrayList<>();
    private static ArrayList<String> left = new ArrayList<>();
    private ArrayList<PrivateOC> privateOCs = new ArrayList<>();
    private ArrayList<PublicOC> publicOCs = new ArrayList<>();
    private ArrayList<ToolC> toolCs = new ArrayList<>();
    private ArrayList<Window> windows = new ArrayList<>();
    private static final Object obj = new Object();
    private static final Object obj2 = new Object();
    private static MatchManager instance = new MatchManager();

    private MatchManager() {
        super();
        //privateOCS
        privateOCs.add(new PrivateOC1());
        privateOCs.add(new PrivateOC2());
        privateOCs.add(new PrivateOC2());
        privateOCs.add(new PrivateOC4());
        privateOCs.add(new PrivateOC5());

        //publicOCS
        publicOCs.add(new PublicOC1());
        publicOCs.add(new PublicOC2());
        publicOCs.add(new PublicOC3());
        publicOCs.add(new PublicOC4());
        publicOCs.add(new PublicOC5());
        publicOCs.add(new PublicOC6());
        publicOCs.add(new PublicOC7());
        publicOCs.add(new PublicOC8());
        publicOCs.add(new PublicOC9());
        publicOCs.add(new PublicOC10());

        //ToolC
        toolCs.add(new ToolC1());
        toolCs.add(new ToolC2());
        toolCs.add(new ToolC3());
        toolCs.add(new ToolC4());
        toolCs.add(new ToolC5());
        toolCs.add(new ToolC6());
        toolCs.add(new ToolC7());
        toolCs.add(new ToolC8());
        toolCs.add(new ToolC9());
        toolCs.add(new ToolC10());
        toolCs.add(new ToolC11());
        toolCs.add(new ToolC12());

        windows.add(new Window1());
        windows.add(new Window2());
        windows.add(new Window3());
        windows.add(new Window4());
        windows.add(new Window5());
        windows.add(new Window6());
        windows.add(new Window7());
        windows.add(new Window8());
        windows.add(new Window9());
        windows.add(new Window10());
        windows.add(new Window11());
        windows.add(new Window12());
        windows.add(new Window13());
        windows.add(new Window14());
        windows.add(new Window15());
        windows.add(new Window16());
        windows.add(new Window17());
        windows.add(new Window18());
        windows.add(new Window19());
        windows.add(new Window20());
        windows.add(new Window21());
        windows.add(new Window22());
        windows.add(new Window23());
        windows.add(new Window24());
    }

    public static MatchManager getInstance() {
        return instance;
    }

    public String startGame(String uUID, String ip, Integer port, boolean isSocket) {

        if (left.contains(uUID) || SReferences.contains(uUID)) {
            Logger.log("Player: " + uUID + " IP: " + ip + " PORT: " + port+ " has connection refused: already playing.\n");
            return "You already playing! Hold on while the server calls you again";
        }

        if (SReferences.getActivePlayer().equals(MAX_ACTIVE_PLAYER_REFS)) {
            Logger.log("Player: " + uUID + " IP: " + ip + " PORT: " + port + " has connection refused: too many players.\n");
            return "Too many players connected. Please try again later. Sorry for that.";
        }

        Logger.log("Player: " + uUID + " IP: " + ip + " PORT: " + port + " SOCKET: "+isSocket+" connection accepted.\n");
        synchronized (obj2) {

            SReferences.addUuidRef(uUID);
            SReferences.addIpRef(uUID, ip);
            SReferences.addPortRef(uUID, port);
            SReferences.addIsSocketRef(uUID, isSocket);

            q.addLast(uUID);
            obj2.notifyAll();
        }

        return "Connections successful. Please wait for other players to connect";
    }

    public static boolean exitGame1(String uUID) {
        synchronized (obj2) {
            if (q.remove(uUID)) {
                System.out.println("Player "+uUID+" leaved platform before game started. Bye.");
                SReferences.removeRef(uUID);
                return true;
            }

            return false;
        }
    }

    public static ArrayList<String> getLeft() {
        return left;
    }

    public static LinkedList<String> getQ() {
        return q;
    }

    public ArrayList<PrivateOC> getPrivateOCs() {
        return privateOCs;
    }

    public ArrayList<PublicOC> getPublicOCs() {
        return publicOCs;
    }

    public ArrayList<ToolC> getToolCs() {
        return toolCs;
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }

    public static Object getObj() {
        return obj;
    }

    public static Object getObj2() {
        return obj2;
    }

    public void setWindows(ArrayList<Window> windows) {
        this.windows = windows;
    }
}