package server;

import server.threads.GameManager;
import shared.Logger;

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

    @Deprecated
    public static ArrayList<Boolean> getIsSocketRef() {
        return isSocketRef;
    }

    @Deprecated
    public static ArrayList<GameManager> getGameRef() {
        return gameRef;
    }

    @Deprecated
    public static ArrayList<Integer> getPortRef() {
        return portRef;
    }

    @Deprecated
    public static ArrayList<Player> getPlayerRef() {
        return playerRef;
    }

    @Deprecated
    public static ArrayList<String> getIpRef() {
        return ipRef;
    }

    @Deprecated
    public static ArrayList<String> getUuidRef() {
        return uuidRef;
    }

    public static Integer getActivePlayer() {
        return activePlayer;
    }

    public static Boolean getIsSocketRefEnhanced(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return isSocketRef.get(index);
    }

    public static GameManager getGameRefEnhanced(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return gameRef.get(index);
    }

    public static Integer getPortRefEnhanced(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return portRef.get(index);
    }

    public static Player getPlayerRefEnhanced(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return playerRef.get(index);
    }

    public static String getIpRefEnhanced(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return ipRef.get(index);
    }

    public static synchronized boolean addGameRef(String s, GameManager gameRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.gameRef.add(index, gameRef);
        return true;
    }

    public static synchronized boolean addIpRef(String s, String ipRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.ipRef.add(index, ipRef);
        return true;
    }

    public static synchronized boolean addIsSocketRef(String s, Boolean isSocketRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.isSocketRef.add(index, isSocketRef);
        return true;
    }

    public static synchronized boolean addPlayerRef(String s, Player playerRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.playerRef.add(index, playerRef);
        return true;
    }

    public static synchronized boolean addPortRef(String s, Integer portRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.portRef.add(index, portRef);
        return true;
    }

    public static synchronized boolean addUuidRef(String uUID) {
        int i = 0;

        if (uuidRef.contains(uUID))
            return false;

        while (i < uuidRef.size() && uuidRef.get(i) != null)
            i++;
        SReferences.uuidRef.add(i, uUID);
        SReferences.activePlayer++;
        return true;
    }

    public static synchronized boolean contains(String s) {
        return uuidRef.contains(s);
    }

    public static synchronized boolean removeRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.gameRef.add(index, null);
        SReferences.ipRef.add(index, null);
        SReferences.isSocketRef.add(index, null);
        SReferences.playerRef.add(index, null);
        SReferences.portRef.add(index, null);
        SReferences.uuidRef.add(index, null);
        SReferences.activePlayer--;
        return true;
    }

}