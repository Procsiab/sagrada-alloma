package client.logic;

import client.threads.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchManager {

    public static final MatchManager INSTANCE = new MatchManager();
    public static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    public final Locker safe = Locker.getSafe();
    public List<PlayerRef> pR = new ArrayList<>();
    private List<PlayerRef> pp1 = new LinkedList<>();
    private List<PlayerRef> pp2 = new LinkedList<>();
    private List<PlayerRef> pp3 = new LinkedList<>();
    private List<PlayerRef> pp4 = new LinkedList<>();
    private AtomicInteger activePlayerRefs = new AtomicInteger(0);

    //make the constructor private so that this class cannot be instantiated from outer classes
    private MatchManager() {}

    //Get the only object available
    public static MatchManager getInstance() {
            return INSTANCE;
        }


    public Integer getActivePlayerRefs(){
        return activePlayerRefs.get();
    }

    public Integer getMaxActivePlayerRefs(){
        return MAX_ACTIVE_PLAYER_REFS;
    }

    /*public Integer getAvailableIDPlayer(){
        Integer k = 0;
        while (k<pR.size() && pR.get(k)!=null){
            k++;
        }

        return k;
    }

    public Integer getAvailableIDMatch(){
        Integer k = 0;
        while (k<m.size() && m.get(k)!=null){
            k++;
        }

        return k;
    }*/


}
