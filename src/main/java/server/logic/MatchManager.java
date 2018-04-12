package server.logic;

import shared.SharedNetworkClient;

import java.util.LinkedList;
import java.util.List;

public class MatchManager {

    public static final MatchManager INSTANCE = new MatchManager();
    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public final Locker safe = Locker.getSafe();
    public List<SharedNetworkClient> pp1 = new LinkedList<>();
    public List<SharedNetworkClient> pp2 = new LinkedList<>();
    public List<SharedNetworkClient> pp3 = new LinkedList<>();
    public List<SharedNetworkClient> pp4 = new LinkedList<>();
    public Integer waitingPlayer = new Integer(0);

    //make the constructor public so that this class cannot be instantiated from outer classes
    public MatchManager() {
    }

    //Get the only object available
    public static MatchManager getInstance() {
        return INSTANCE;
    }

    public Integer getWaitingPlayer() {
        return waitingPlayer;
    }

    public Integer getMaxActivePlayerRefs() {
        return MAX_ACTIVE_PLAYER_REFS;
    }

    public boolean startGame(SharedNetworkClient client, Integer nMates) {

        synchronized (safe.Lock1) {
            if (waitingPlayer == MAX_ACTIVE_PLAYER_REFS)
                return false;
            waitingPlayer++;
        }
        if (nMates == 2) {
            synchronized (safe.Lock2.get(0)) {
                pp2.add(client);
            }
        } else if (nMates == 3) {
            synchronized (safe.Lock2.get(1)) {
                pp3.add(client);
            }
        } else if (nMates == 4) {
            synchronized (safe.Lock2.get(2)) {
                pp4.add(client);
            }
        }
        return true;
    }
}