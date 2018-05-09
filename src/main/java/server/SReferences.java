package server;

import shared.GameManager;
import shared.Player;

import java.util.ArrayList;

public class SReferences {

    //CAUTION: Do not synchronize on Safe.slock2

    private static Integer activePlayer = 0;

    private static ArrayList<String> uuidRef = new ArrayList<>();
    private static ArrayList<String> ipRef = new ArrayList<>();
    private static ArrayList<Integer> portRef = new ArrayList<>();
    private static ArrayList<Boolean> isSocketRef = new ArrayList<>();
    private static ArrayList<Player> playerRef = new ArrayList<>();
    private static ArrayList<GameManager> gameRef = new ArrayList<>();


    public static synchronized ArrayList<Boolean> getIsSocketRef() {
        return isSocketRef;
    }

    public static synchronized ArrayList<GameManager> getGameRef() {
        return gameRef;
    }

    public static synchronized ArrayList<Integer> getPortRef() {
        return portRef;
    }

    public static synchronized ArrayList<Player> getPlayerRef() {
        return playerRef;
    }

    public static synchronized ArrayList<String> getIpRef() {
        return ipRef;
    }

    public static synchronized ArrayList<String> getUuidRef() {
        return uuidRef;
    }

    public static synchronized Integer getActivePlayer() {
        return activePlayer;
    }

    public static synchronized void addGameRef(String s, GameManager gameRef) {
        SReferences.gameRef.add(SReferences.getUuidRef().indexOf(s), gameRef);
    }

    public static synchronized void addIpRef(String s, String ipRef) {
        SReferences.ipRef.add(SReferences.getUuidRef().indexOf(s), ipRef);
    }

    public static synchronized void addIsSocketRef(String s, Boolean isSocketRef) {
        SReferences.isSocketRef.add(SReferences.getUuidRef().indexOf(s), isSocketRef);
    }

    public static synchronized void addPlayerRef(String s, Player playerRef) {
        SReferences.playerRef.add(SReferences.getUuidRef().indexOf(s), playerRef);
    }

    public static synchronized void addPortRef(String s, Integer portRef) {
        SReferences.portRef.add(SReferences.getUuidRef().indexOf(s), portRef);
    }

    public static synchronized void addUuidRef(String uUID) {
        int i = 0;
        for (String u : uuidRef
                ) {
            if (u == null) {
                uuidRef.add(i, uUID);
            }
            i++;
        }
    }

    public static synchronized void removeGameRef(String s) {
        SReferences.gameRef.add(SReferences.getUuidRef().indexOf(s), null);
    }

    public static synchronized void removeIpRef(String s) {
        SReferences.ipRef.add(SReferences.getUuidRef().indexOf(s), null);
    }

    public static synchronized void removeIsSocketRef(String s) {
        SReferences.isSocketRef.add(SReferences.getUuidRef().indexOf(s), null);
    }

    public static synchronized void removePlayerRef(String s) {
        SReferences.playerRef.add(SReferences.getUuidRef().indexOf(s),null);
    }

    public static synchronized void removePortRef(String s) {
        SReferences.portRef.add(SReferences.getUuidRef().indexOf(s), null);
    }

    public static synchronized void removeUuidRef(String s) {
        SReferences.uuidRef.add(SReferences.getUuidRef().indexOf(s), null);
    }

    public static synchronized void incrementActivePlayer() {
        SReferences.activePlayer++;
    }

    public static synchronized void decrementActivePlayer() {
        SReferences.activePlayer--;
    }
}


