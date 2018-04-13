package server.logic;

import shared.SharedClientGame;
import shared.SharedNetworkClient;
import shared.SharedServerMatchManager;

import java.util.LinkedList;
import java.util.List;

public class MatchManager implements SharedServerMatchManager {

    public static final MatchManager INSTANCE = new MatchManager();
    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public final Locker safe = Locker.getSafe();
    public List<SharedClientGame> pp2 = new LinkedList<>();
    public List<SharedClientGame> pp3 = new LinkedList<>();
    public List<SharedClientGame> pp4 = new LinkedList<>();
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

    public String startGame(SharedClientGame client, Integer nMates) {

        synchronized (safe.Lock1) {
            if (waitingPlayer == MAX_ACTIVE_PLAYER_REFS)
                return "Connection successful. Please wait for other players to connect";
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
        return "Too many incoming requests, please try again later. Sorry for that.";
    }
}