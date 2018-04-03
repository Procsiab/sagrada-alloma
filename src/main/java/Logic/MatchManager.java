package Logic;

import Logic.Concurrency.TurnManager;
import Network.Server.mainServer;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchManager {

    private static final MatchManager Instance = new MatchManager();
    private static final Integer maxActivePlayer = 250;
    private final Locker Safe = Locker.getSafe();
    private static String[][] bindingConfig = new String[maxActivePlayer][3];
    private LinkedList<PlayerRef> pR = new LinkedList<PlayerRef>();
    private LinkedList<Player> p = new LinkedList<Player>();
    private LinkedList<Match> m = new LinkedList<Match>();
    private LinkedList<PlayerRef> pp1 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp2 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp3 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp4 = new LinkedList<PlayerRef>();
    private AtomicInteger activePlayerRef = new AtomicInteger(0);

    //make the constructor private so that this class cannot be instantiated from outer classes
    private MatchManager() {}

        //Get the only object available
        public static MatchManager getInstance() {
            return Instance;
        }

        public LinkedList<PlayerRef> getpR(){
            return pR;
        }

        public LinkedList<Player> getP() {
            return p;
        }

        public LinkedList<Match> getM() {
            return m;
    }

    public Integer getAvailableIDPlayer(){
        return 0;
    }

    public Integer getAvailableIDMatch(){
        return 0;
    }

    public void createAndBindUpd(String MAC, String IP, String Port, String Name, Integer nMates) {

        tryStartMatch();
    }

    public void tryStartMatch() {
        LinkedList<Player> players= new LinkedList<>();
        Integer IDMatch = getAvailableIDMatch();
        Match match = new Match(IDMatch, players);

        ConcurrencyManager.submit(new TurnManager(IDMatch, match, players));
    }


}
