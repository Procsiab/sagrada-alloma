package server;

import server.threads.GameManager;

import java.util.ArrayList;
import java.util.List;

public class SReferences {

    //CAUTION: Do not synchronize on MatchManager.obj2, possible deadlock

    private static Integer activePlayer = 0;

    private static ArrayList<String> uuidRef = new ArrayList<>();
    private static ArrayList<String> ipRef = new ArrayList<>();
    private static ArrayList<Integer> portRef = new ArrayList<>();
    private static ArrayList<Boolean> isSocketRef = new ArrayList<>();
    private static ArrayList<String> nickNameRef = new ArrayList<>();
    private static ArrayList<Player> playerRef = new ArrayList<>();
    private static ArrayList<GameManager> gameRef = new ArrayList<>();


    public static synchronized Integer getActivePlayer() {
        return activePlayer;
    }

    public static synchronized Boolean getIsSocketRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index.equals(-1))
            return null;
        return isSocketRef.get(index);
    }

    public static synchronized GameManager getGameRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index.equals(-1))
            return null;
        return gameRef.get(index);
    }

    public static synchronized Integer getPortRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index.equals(-1))
            return null;
        return portRef.get(index);
    }

    public static synchronized Player getPlayerRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index.equals(-1))
            return null;
        return playerRef.get(index);
    }

    public static synchronized String getIpRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index.equals(-1))
            return null;
        return ipRef.get(index);
    }

    public static synchronized String getNickNameRef(String s) {
        Integer index = uuidRef.indexOf(s);
        if (index.equals(-1))
            return null;
        return nickNameRef.get(index);
    }

    public static synchronized void addGameRef(String s, GameManager gameRef) {
        Integer index = uuidRef.indexOf(s);
        SReferences.gameRef.set(index, gameRef);
    }

    public static synchronized Integer getIndexOfGameRef(GameManager gameManager){
        return gameRef.indexOf(gameManager);
    }

    public static synchronized void addIpRef(String s, String ipRef) {
        Integer index = uuidRef.indexOf(s);
        SReferences.ipRef.set(index, ipRef);
    }

    public static synchronized void addIsSocketRef(String s, Boolean isSocketRef) {
        Integer index = uuidRef.indexOf(s);
        SReferences.isSocketRef.set(index, isSocketRef);
    }

    public static synchronized void addPlayerRef(String s, Player playerRef) {
        Integer index = uuidRef.indexOf(s);
        SReferences.playerRef.set(index, playerRef);
    }

    public static synchronized void addPortRef(String s, Integer portRef) {
        Integer index = uuidRef.indexOf(s);
        SReferences.portRef.set(index, portRef);
    }

    public static synchronized void addNickNameRef(String s, String nickNameRef) {
        Integer index = uuidRef.indexOf(s);
        SReferences.nickNameRef.set(index, nickNameRef);
    }

    public static synchronized Boolean addUuidRefEnhanced(String uUID) {
        int i = 0;

        if (uuidRef.contains(uUID))
            return false;

        while (i < uuidRef.size() && uuidRef.get(i) != null)
            i++;

        if (i == uuidRef.size()) {
            SReferences.uuidRef.add(uUID);
            SReferences.portRef.add(null);
            SReferences.playerRef.add(null);
            SReferences.isSocketRef.add(null);
            SReferences.ipRef.add(null);
            SReferences.gameRef.add(null);
            SReferences.nickNameRef.add(null);
        } else {
            SReferences.uuidRef.set(i, uUID);
        }
        SReferences.activePlayer++;
        return true;
    }

    public static synchronized Boolean contains(String s) {
        return uuidRef.contains(s);
    }

    public static synchronized Boolean checkNickNameRef(String nickNameRef, List<String> queue) {
        for (String uUID :
                queue) {
            if (getNickNameRef(uUID).equals(nickNameRef))
                return false;
        }
        return true;
    }

    public static synchronized void removeRef(String s) {
        Integer index = uuidRef.indexOf(s);
        SReferences.gameRef.set(index, null);
        SReferences.ipRef.set(index, null);
        SReferences.isSocketRef.set(index, null);
        SReferences.playerRef.set(index, null);
        SReferences.portRef.set(index, null);
        SReferences.nickNameRef.set(index, null);
        SReferences.uuidRef.set(index, null);
        SReferences.activePlayer--;
    }

}