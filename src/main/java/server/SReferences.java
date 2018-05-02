package server;

import server.threads.GameManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class SReferences {

    //CAUTION: Do not synchronize on Safe.slock2


    public static ArrayList<String> uuidRef = new ArrayList<>();
    public static ArrayList<String> ipRef = new ArrayList<>();
    public static ArrayList<Integer> portRef = new ArrayList<>();
    public static ArrayList<Boolean> isSocketRef = new ArrayList<>();
    public static ArrayList<Player> playerRef = new ArrayList<>();
    public static ArrayList <GameManager> gameRef = new ArrayList<>();

    public static ArrayList<Boolean> getIsSocketRef() {
        return isSocketRef;
    }

    public static ArrayList<GameManager> getGameRef() {
        return gameRef;
    }

    public static ArrayList<Integer> getPortRef() {
        return portRef;
    }

    public static ArrayList<Player> getPlayerRef() {
        return playerRef;
    }

    public static ArrayList<String> getIpRef() {
        return ipRef;
    }

    public static ArrayList<String> getUuidRef() {
        return uuidRef;
    }

    public static void setGameRef(String s, GameManager gameRef) {
        SReferences.gameRef.add(SReferences.getUuidRef().indexOf(s),gameRef);
    }

    public static void setIpRef(String s, String ipRef) {
        SReferences.ipRef.add(SReferences.getUuidRef().indexOf(s),ipRef);
    }

    public static void setIsSocketRef(String s, Boolean isSocketRef) {
        SReferences.isSocketRef.add(SReferences.getUuidRef().indexOf(s),isSocketRef);
    }

    public static void setPlayerRef(String s, Player playerRef) {
        SReferences.playerRef.add(SReferences.getUuidRef().indexOf(s), playerRef);
    }

    public static void setPortRef(String s, Integer portRef) {
        SReferences.portRef.add(SReferences.getUuidRef().indexOf(s),portRef);
    }

    public static void setUuidRef(String s, String uuidRef) {
        SReferences.uuidRef.add(SReferences.getUuidRef().indexOf(s), uuidRef);
    }
}


