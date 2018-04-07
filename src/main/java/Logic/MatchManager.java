package Logic;

import Logic.Concurrency.TurnManager;
import Logic.Concurrency.TurnManagerSolo;
import Network.Server.mainServer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchManager {

    private static final MatchManager Instance = new MatchManager();
    private static final Integer maxActivePlayerRefs = 250;
    private final Locker Safe = Locker.getSafe();
    private static String[][] bindingConfig = new String[maxActivePlayerRefs][3];
    private ArrayList<PlayerRef> pR = new ArrayList<PlayerRef>();
    private ArrayList<Player> p = new ArrayList<Player>();
    private ArrayList<Match> m = new ArrayList<Match>();
    private LinkedList<PlayerRef> pp1 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp2 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp3 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp4 = new LinkedList<PlayerRef>();
    private AtomicInteger activePlayerRefs = new AtomicInteger(0);

    //make the constructor private so that this class cannot be instantiated from outer classes
    private MatchManager() {}

    //Get the only object available
    public static MatchManager getInstance() {
            return Instance;
        }

    public ArrayList<PlayerRef> getpR(){
            return pR;
        }

    public ArrayList<Player> getP() {
            return p;
        }

    public ArrayList<Match> getM() {
            return m;
    }

    public Integer getActivePlayerRefs(){
        return activePlayerRefs.get();
    }

    public Integer getMaxActivePlayerRefs(){
        return maxActivePlayerRefs;
    }

    public Integer getAvailableIDPlayer(){
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
    }

    public void createAndBindUpd(String MAC, String IP, String Port, String Name, Integer nMates) {

        synchronized (Safe.allQPPMA) {
            Integer IDP = getAvailableIDPlayer();

            bindingConfig[IDP][0] = MAC;
            bindingConfig[IDP][1] = IP;
            bindingConfig[IDP][2] = Port;

            PlayerRef newPlayerRef = new PlayerRef(IDP, Name, nMates);
            pR.add(IDP, newPlayerRef);
            activePlayerRefs.getAndIncrement();
            //synchronize network to deny access when maxActivePlayRef is reached
            if (nMates == 1)
                pp1.add(newPlayerRef);
            else if (nMates == 2)
                pp2.add(newPlayerRef);
            else if (nMates == 3)
                pp3.add(newPlayerRef);
            else if (nMates == 4)
                pp4.add(newPlayerRef);
        }
        tryStartMatch();
    }

    public void deletePlayer(Integer IDPlayer){
        synchronized (Safe.allQPPMA){
            if(p.get(IDPlayer) == null){
                PlayerRef current = pR.get(IDPlayer);
                if(current.getnMates() == 1)
                    pp1.remove(IDPlayer);
                if(current.getnMates() == 2)
                    pp2.remove(IDPlayer);
                if(current.getnMates() == 3)
                    pp3.remove(IDPlayer);
                if(current.getnMates() == 4)
                    pp4.remove(IDPlayer);
                pR.remove(IDPlayer);
            }
            else
                MatchManager.getInstance().getM().get(MatchManager.getInstance().
                        getP().get(IDPlayer).getIDMatch()).quit(IDPlayer);
        }
    }

    public void tryStartMatch() {
        Integer k;

        synchronized (Safe.allQPPMA) {
            if (pp1.size() % 1 == 0) {
                k = getAvailableIDMatch();
                PlayerRef playR = pp1.remove(0);
                Player player1 = new Player(playR.getID(), k, playR.getName());
                ArrayList<Player> players = new ArrayList<Player>();
                players.add(player1);
                p.add(player1.getIDPlayer(),player1);
                Match m1 = new Match(k, players);
                m.add(k,m1);
                ConcurrencyManager.submit(new TurnManagerSolo());
            }

            if (pp2.size() % 2 == 0) {
                k = getAvailableIDMatch();
                PlayerRef playR1 = pp2.remove(0);
                PlayerRef playR2 = pp2.remove(0);
                Player player1 = new Player(playR1.getID(), k, playR1.getName());
                Player player2 = new Player(playR2.getID(), k, playR1.getName());
                ArrayList<Player> players = new ArrayList<Player>();
                p.add(player1.getIDPlayer(),player1);
                p.add(player2.getIDPlayer(),player2);
                players.add(player1);
                players.add(player2);
                Match m2 = new Match(k, players);
                m.add(k,m2);
                ConcurrencyManager.submit(new TurnManager(k, m2, players));
            }

            if (pp3.size() % 3 == 0) {
                k = getAvailableIDMatch();
                PlayerRef playR1 = pp3.remove();
                PlayerRef playR2 = pp3.remove();
                PlayerRef playR3 = pp3.remove();
                Player player1 = new Player(playR1.getID(), k, playR1.getName());
                Player player2 = new Player(playR2.getID(), k, playR1.getName());
                Player player3 = new Player(playR3.getID(), k, playR3.getName());
                ArrayList<Player> players = new ArrayList<Player>();
                p.add(player1.getIDPlayer(),player1);
                p.add(player2.getIDPlayer(),player2);
                p.add(player3.getIDPlayer(),player3);
                players.add(player1);
                players.add(player2);
                players.add(player3);
                Match m3 = new Match(k, players);
                m.add(k,m3);
                ConcurrencyManager.submit(new TurnManager(k, m3, players));
            }

            if (pp4.size() % 4 == 0) {
                k = getAvailableIDMatch();
                PlayerRef playR1 = pp3.remove();
                PlayerRef playR2 = pp3.remove();
                PlayerRef playR3 = pp3.remove();
                PlayerRef playR4 = pp4.remove();
                Player player1 = new Player(playR1.getID(), k, playR1.getName());
                Player player2 = new Player(playR2.getID(), k, playR1.getName());
                Player player3 = new Player(playR3.getID(), k, playR3.getName());
                Player player4 = new Player(playR4.getID(), k, playR4.getName());
                ArrayList<Player> players = new ArrayList<Player>();
                p.add(player1.getIDPlayer(),player1);
                p.add(player2.getIDPlayer(),player2);
                p.add(player3.getIDPlayer(),player3);
                p.add(player4.getIDPlayer(),player4);
                players.add(player1);
                players.add(player2);
                players.add(player3);
                players.add(player4);
                Match m4 = new Match(k, players);
                m.add(k,m4);
                ConcurrencyManager.submit(new TurnManager(k, m4, players));
            }
        }
    }

}
