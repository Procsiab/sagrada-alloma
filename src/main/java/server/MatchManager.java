package server;

import server.abstracts.*;
import shared.logic.Locker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatchManager {
    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public static LinkedList<String> q = new LinkedList<>();
    public static ArrayList<String> left = new ArrayList<>();
    public final Locker safe = Locker.getSafe();
    public List<String> nickNames = new ArrayList<>();
    public List<PrivateOC> privateOCs = new ArrayList<>();
    public List<PublicOC> publicOCs = new ArrayList<>();
    public List<ToolC> toolCS = new ArrayList<>();
    public List<Frame> frames = new ArrayList<>();
    public List<Window> windows = new ArrayList<>();
    public List<ScoreMarker> scoreMarkers = new ArrayList<>();
    public Object locker = new Object();

    private static MatchManager instance = new MatchManager();

    public static MatchManager getInstance() {
        return instance;
    }



    private MatchManager() {
        //fill ArrayLists, such as toolCS... with objects
        super();
    }


    public void print() {
        System.out.println("Hwe");
    }

    public String startGame(String uuid, String ip, Integer port, boolean isSocket) {

        System.out.println("wuednclk");

        if (left.contains(uuid)) {
            return "You already playing asshole! Hold on while the server calls you again";
        }

        if (SReferences.uuidRef.size() == MAX_ACTIVE_PLAYER_REFS) {
            return "Too many players connected. Please try again later. Sorry for that.";
        }

        synchronized (safe.sLock2) {
            q.addLast(uuid);

            SReferences.uuidRef.add(uuid);
            int pos = SReferences.uuidRef.indexOf(uuid);
            SReferences.ipRef.add(pos,ip);
            SReferences.portRef.add(pos,port);
            SReferences.isSocketRef.add(pos,isSocket);
            safe.sLock2.notifyAll();
        }
        return "Connections successful. Please wait for other players to connect";


    }

    public boolean exitGame1(String uUID) {
        if (!q.remove(uUID))
            return false;
        //you may call a function client-side that delete client from its match
        return true;
    }

    public boolean setNickName(String nickName) {
        synchronized (safe.sLock3) {
            if (nickNames.contains(nickName))
                return false;
            else
                nickNames.add(nickName);
            return true;
        }
    }
}