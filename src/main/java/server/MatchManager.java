package server;

import server.abstractsServer.PublicOC;
import server.abstractsServer.ToolC;
import shared.Logger;
import shared.abstractsShared.*;
import shared.cardsShared.privateOC.*;
import server.cardsServer.publicOC.*;
import server.cardsServer.toolC.*;
import shared.logic.Locker;
import shared.cardsShared.windows.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatchManager {
    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public static LinkedList<String> q = new LinkedList<>();
    public static ArrayList<String> left = new ArrayList<>();
    public final Locker safe = Locker.getSafe();
    public List<PrivateOC> privateOCs = new ArrayList<>();
    public List<PublicOC> publicOCs = new ArrayList<>();
    public List<ToolC> toolCs = new ArrayList<>();
    public List<Window> windows = new ArrayList<>();
    public static final Object obj = new Object();

    private static MatchManager instance = new MatchManager();

    public static MatchManager getInstance() {
        return instance;
    }


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


    public String startGame(String uUID, String ip, Integer port, boolean isSocket) {

        if (left.contains(uUID) || SReferences.contains(uUID)) {
            Logger.log("Player " + uUID + ": has connection refused: already playing.");
            return "You already playing asshole! Hold on while the server calls you again";
        }

        if (SReferences.getActivePlayer().equals(MAX_ACTIVE_PLAYER_REFS)) {
            Logger.log("Player " + uUID + ": has connection refused: too many players.");
            return "Too many players connected. Please try again later. Sorry for that.";
        }

        Logger.log("Player " + uUID + ": Connection accepted");

        SReferences.addUuidRef(uUID);
        SReferences.addIpRef(uUID, ip);
        SReferences.addPortRef(uUID, port);
        SReferences.addIsSocketRef(uUID, isSocket);

        synchronized (safe.sLock2) {
            q.addLast(uUID);
            safe.sLock2.notifyAll();
        }

        return "Connections successful. Please wait for other players to connect";
    }

    public boolean exitGame1(String uUID) {
        synchronized (safe.sLock2) {
            if (!q.remove(uUID))
                return false;
        }

        SReferences.removeRef(uUID);

        //you may call a function client-side that delete client from its match
        return true;
    }

}