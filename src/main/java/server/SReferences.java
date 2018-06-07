package server;

import server.threads.GameManager;
import shared.Logger;
import shared.network.SharedMiddlewareClient;
import shared.network.SharedMiddlewareServer;

import java.util.ArrayList;

public class SReferences {

    //CAUTION: Do not synchronize on MatchManager.obj2, possible deadlock

    private static Integer activePlayer = 0;

    private static ArrayList<String> uuidRef = new ArrayList<>();
    private static ArrayList<String> ipRef = new ArrayList<>();
    private static ArrayList<Integer> portRef = new ArrayList<>();
    private static ArrayList<Boolean> isSocketRef = new ArrayList<>();
    private static ArrayList<Player> playerRef = new ArrayList<>();
    private static ArrayList<GameManager> gameRef = new ArrayList<>();


    public static synchronized Integer getActivePlayer() {
        return activePlayer;
    }

    public static synchronized Boolean getIsSocketRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return isSocketRef.get(index);
    }

    public static synchronized GameManager getGameRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return gameRef.get(index);
    }

    public static synchronized Integer getPortRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return portRef.get(index);
    }

    public static synchronized Player getPlayerRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return playerRef.get(index);
    }

    public static synchronized String getIpRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return null;
        return ipRef.get(index);
    }

    public static synchronized boolean addGameRef(String s, GameManager gameRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.gameRef.set(index, gameRef);
        return true;
    }

    public static synchronized boolean addIpRef(String s, String ipRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.ipRef.set(index, ipRef);
        return true;
    }

    public static synchronized boolean addIsSocketRef(String s, Boolean isSocketRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.isSocketRef.set(index, isSocketRef);
        return true;
    }

    public static synchronized boolean addPlayerRef(String s, Player playerRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.playerRef.set(index, playerRef);
        return true;
    }

    public static synchronized boolean addPortRef(String s, Integer portRef) {
        Integer index = uuidRef.indexOf(s);
        if (index == -1)
            return false;
        SReferences.portRef.set(index, portRef);
        return true;
    }

    public static synchronized boolean addUuidRefEnhanced(String uUID) {
        int i = 0;

        if (uuidRef.contains(uUID))
            return false;

        while (i < uuidRef.size() && uuidRef.get(i) != null)
            i++;

        if(i == uuidRef.size()){
            SReferences.uuidRef.add(uUID);
            SReferences.portRef.add(null);
            SReferences.playerRef.add(null);
            SReferences.isSocketRef.add(null);
            SReferences.ipRef.add(null);
            SReferences.gameRef.add(null);
        } else {
            SReferences.uuidRef.set(i, uUID);
        }
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
        SReferences.gameRef.set(index, null);
        SReferences.ipRef.set(index, null);
        SReferences.isSocketRef.set(index, null);
        SReferences.playerRef.set(index, null);
        SReferences.portRef.set(index, null);
        SReferences.uuidRef.set(index, null);
        SReferences.activePlayer--;
        return true;
    }

}