package Logic.Concurrency;

import Logic.ConcurrencyManager;
import Logic.Locker;
import Logic.Match;
import Logic.Player;

import java.util.LinkedList;

public class TurnManager extends GeneralTask {

    private final Integer IDMatch;
    private final Match match;
    private final LinkedList<Player> players;
    private final Integer sleepTime;

    private Locker Safe = Locker.getSafe();

    public TurnManager(Integer IDMatch, Match match, LinkedList<Player> players){
        this.IDMatch = IDMatch;
        this.match = match;
        this.players = players;
        this.sleepTime = 10000;
    }




    public void enable(Player player){
        //enable selected player, by invoking method on the client which lead to P2P architecture.
        //As other methods invoked from server may be needed, P2P appear to be the most efficient idea.
    }



    @Override
    public void run() {
        int j = 1;
        int i = 1;
        while(j<=10){
            while(i<= players.size()){
                enable(players.get(i-1));
                synchronized (Safe.action.get(IDMatch)) {
                    while (match.getAction() == 0)
                        try{
                        Safe.action.get(IDMatch).wait(sleepTime);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                    //enabled by notify in setDicePositions, not needed in other invocations
                    match.setAction(0);
                }
                i++;
            }
            while (i>=1){
                enable(players.get(i-1));
                synchronized (Safe.action.get(IDMatch)) {
                    while (match.getAction() == 0)
                        try {
                            Safe.action.get(IDMatch).wait(sleepTime);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    //enabled by notify in setDicePositions, not needed in other invocations
                    match.setAction(0);
                }
                i--;
            }
            j++;
        }
    }
}
