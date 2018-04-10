package server.logic;

import server.threads.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchManager {

    private static final MatchManager INSTANCE = new MatchManager();
    private static final Integer MAX_ACTIVE_PLAYER_REFS = 250;
    private final Locker safe = Locker.getSafe();
    private static String[][] bindingConfig = new String[MAX_ACTIVE_PLAYER_REFS][3];
    private List<PlayerRef> pR = new ArrayList<>();
    private List<Player> p = new ArrayList<>();
    private List<Match> m = new ArrayList<>();
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

    public List<PlayerRef> getpR(){
            return pR;
        }

    public List<Player> getP() {
            return p;
        }

    public List<Match> getM() {
            return m;
    }

    public Integer getActivePlayerRefs(){
        return activePlayerRefs.get();
    }

    public Integer getMaxActivePlayerRefs(){
        return MAX_ACTIVE_PLAYER_REFS;
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

    public void createAndBindUpd(String mac, String ip, String port, String name, Integer nMates) {

        synchronized (safe.allQPPMA) {
            Integer idPlayer = getAvailableIDPlayer();

            bindingConfig[idPlayer][0] = mac;
            bindingConfig[idPlayer][1] = ip;
            bindingConfig[idPlayer][2] = port;

            PlayerRef newPlayerRef = new PlayerRef(idPlayer, name, nMates);
            pR.add(idPlayer, newPlayerRef);
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

    public void deletePlayer(Integer idPlayer){
        synchronized (safe.allQPPMA){
            if(p.get(idPlayer) == null){
                PlayerRef current = pR.get(idPlayer);
                if(current.getnMates() == 1)
                    pp1.remove(idPlayer);
                if(current.getnMates() == 2)
                    pp2.remove(idPlayer);
                if(current.getnMates() == 3)
                    pp3.remove(idPlayer);
                if(current.getnMates() == 4)
                    pp4.remove(idPlayer);
                pR.remove(idPlayer);
            }
            else
                MatchManager.getInstance().getM().get(MatchManager.getInstance().
                        getP().get(idPlayer).getIdMatch()).quit(idPlayer);
        }
    }

    public void tryStartMatch() {
        Integer k;

        synchronized (safe.allQPPMA) {
            if (pp1.size() % 1 == 0) { //TODO whatever % 1 = 0! Change comparison
                k = getAvailableIDMatch();
                PlayerRef playR = pp1.remove(0);
                Player player1 = new Player(playR.getID(), k, playR.getName());
                List<Player> players = new ArrayList<>();
                players.add(player1);
                p.add(player1.getIdPlayer(),player1);
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
                List<Player> players = new ArrayList<>();
                p.add(player1.getIdPlayer(),player1);
                p.add(player2.getIdPlayer(),player2);
                players.add(player1);
                players.add(player2);
                Match m2 = new Match(k, players);
                m.add(k,m2);
                ConcurrencyManager.submit(new TurnManager(k, m2, players));
            }

            if (pp3.size() % 3 == 0) {
                k = getAvailableIDMatch();
                PlayerRef playR1 = pp3.remove(0);
                PlayerRef playR2 = pp3.remove(0);
                PlayerRef playR3 = pp3.remove(0);
                Player player1 = new Player(playR1.getID(), k, playR1.getName());
                Player player2 = new Player(playR2.getID(), k, playR1.getName());
                Player player3 = new Player(playR3.getID(), k, playR3.getName());
                List<Player> players = new ArrayList<>();
                p.add(player1.getIdPlayer(),player1);
                p.add(player2.getIdPlayer(),player2);
                p.add(player3.getIdPlayer(),player3);
                players.add(player1);
                players.add(player2);
                players.add(player3);
                Match m3 = new Match(k, players);
                m.add(k,m3);
                ConcurrencyManager.submit(new TurnManager(k, m3, players));
            }

            if (pp4.size() % 4 == 0) {
                k = getAvailableIDMatch();
                PlayerRef playR1 = pp3.remove(0);
                PlayerRef playR2 = pp3.remove(0);
                PlayerRef playR3 = pp3.remove(0);
                PlayerRef playR4 = pp4.remove(0);
                Player player1 = new Player(playR1.getID(), k, playR1.getName());
                Player player2 = new Player(playR2.getID(), k, playR1.getName());
                Player player3 = new Player(playR3.getID(), k, playR3.getName());
                Player player4 = new Player(playR4.getID(), k, playR4.getName());
                List<Player> players = new ArrayList<>();
                p.add(player1.getIdPlayer(),player1);
                p.add(player2.getIdPlayer(),player2);
                p.add(player3.getIdPlayer(),player3);
                p.add(player4.getIdPlayer(),player4);
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
