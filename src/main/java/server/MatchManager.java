package server;

import shared.Logger;
import shared.abstracts.*;
import shared.cards.privateOC.*;
import shared.cards.publicOC.*;
import shared.cards.toolC.*;
import shared.logic.Locker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatchManager implements Serializable {
    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public static LinkedList<String> q = new LinkedList<>();
    public static ArrayList<String> left = new ArrayList<>();
    public final Locker safe = Locker.getSafe();
    public List<PrivateOC> privateOCs = new ArrayList<>();
    public List<PublicOC> publicOCs = new ArrayList<>();
    public List<ToolC> toolCs = new ArrayList<>();
    public List<Window> windows = new ArrayList<>();
    public static transient final Object obj = new Object();

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
    }


    public String startGame(String uuid, String ip, Integer port, boolean isSocket) {

        if (left.contains(uuid)) {
            Logger.log("Player " + uuid + ": Connection refuse: already playing.");
            return "You already playing asshole! Hold on while the server calls you again";
        }

        if (SReferences.getActivePlayer().equals(MAX_ACTIVE_PLAYER_REFS)) {
            Logger.log("Player " + uuid + ": Connection refuse: too many players.");
            return "Too many players connected. Please try again later. Sorry for that.";
        }

        Logger.log("Player " + uuid + ": Connection accepted");

        SReferences.addUuidRef(uuid);
        SReferences.addIpRef(uuid, ip);
        SReferences.addPortRef(uuid, port);
        SReferences.addIsSocketRef(uuid, isSocket);

        synchronized (safe.sLock2) {
            q.addLast(uuid);
            safe.sLock2.notifyAll();
        }

        return "Connections successful. Please wait for other players to connect";
    }

    public boolean exitGame1(String uUID) {
        synchronized (safe.sLock2) {
            if (!q.remove(uUID))
                return false;
        }

        SReferences.removeUuidRef(uUID);
        SReferences.removeIpRef(uUID);
        SReferences.removePortRef(uUID);
        SReferences.removeIsSocketRef(uUID);

        //you may call a function client-side that delete client from its match
        return true;
    }

}