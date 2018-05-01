package server;

import server.abstracts.*;
import shared.SharedClientGame;
import shared.SharedServerPlayer;
import shared.logic.Locker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatchManager {
    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public static ArrayList<Integer> left = new ArrayList<>();
    public final Locker safe = Locker.getSafe();
    public List<String> nickNames = new ArrayList<>();
    public LinkedList<SharedClientGame> q = new LinkedList<>();
    public Integer waitingPlayer = 0;
    public List<PrivateOC> privateOCs = new ArrayList<>();
    public List<PublicOC> publicOCs = new ArrayList<>();
    public List<ToolC> toolCS = new ArrayList<>();
    public List<Frame> frames = new ArrayList<>();
    public List<Window> windows = new ArrayList<>();
    public List<ScoreMarker> scoreMarkers = new ArrayList<>();
    public Object locker = new Object();

    private static MatchManager instance = null;

    public static MatchManager getInstance() {
        return instance;
    }

    public static void setInstance() {
        if(instance == null) {
            instance = new MatchManager();
        }
    }

    private MatchManager() {
        super();
        //initialize every ArrayList
    }

    public Integer getWaitingPlayer() {
        return waitingPlayer;
    }

    public void print(){
        System.out.println("Hwe");
    }

    public String startGame(String uuid, String ip, Integer port, boolean isSocket) {
        //TODO Get rid of pre-existing SharedClientGame client
        SharedClientGame client = null;
        synchronized (safe.sLock1) {
            waitingPlayer++;

            if (left.remove(client)){
                waitingPlayer--;
                return "You already playing asshole!";
            }

            if (waitingPlayer.equals(MAX_ACTIVE_PLAYER_REFS+1)){
                waitingPlayer--;
                return "Too many incoming requests, please try again later. Sorry for that.";
            }
        }
        synchronized (safe.sLock2) {
            q.addLast(client);
            //TODO Check if is correct
            SReferences.uuidRef.add(uuid);
            SReferences.ipRef.add(ip);
            SReferences.portRef.add(port);
            SReferences.isSocketRef.add(isSocket);
            safe.sLock2.notifyAll();
        }
        return "Connections successful. Please wait for other players to connect";


    }

    public boolean exitGame1(SharedClientGame client){
        if(!q.remove(client)){
            //you may call a function client-side that delete client from its match
            return false;
        }
        synchronized (safe.sLock1){
            waitingPlayer--;
        }
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