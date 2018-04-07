package Logic.Concurrency;

import Logic.ConcurrencyManager;
import Logic.Locker;
import Logic.Match;
import Logic.Player;

import java.util.ArrayList;

public class TurnManager extends GeneralTask {

    private final Integer IDMatch;
    private final Match match;
    private final ArrayList<Player> players;
    private final Integer sleepTime;

    private Locker Safe = Locker.getSafe();

    public TurnManager(Integer IDMatch, Match match, ArrayList<Player> players){
        this.IDMatch = IDMatch;
        this.match = match;
        this.players = players;
        this.sleepTime = 10000;
    }




    public void enable(Player player){
        //enable selected player, by invoking method on the client-side. Shut all others.
    }



    @Override
    public void run() {

        //show shuffle Private Objective Card animation on all clients
        //show 4 private objective cards and 1 window frame player board. Player will choose only 1 card.
        //give each player the number of favor Tokens indicated on their card
        //give each player the appropriate score marker
        //place 3 tool cards in the center face up
        //place 3 public obj cards in the center face up
        //first player is the player.get(0)

        int j = 1;
        int i = 1;
        while(j<=10){
            while(i<= players.size()){// not need to sync players as they are a copy and not accessed elsewhere
                enable(players.get(i-1));
                synchronized (Safe.actionL.get(IDMatch)) {
                    while (match.getAction() == 0)
                        try{
                        Safe.actionL.get(IDMatch).wait(sleepTime);
                        match.setAction(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    while (match.getLoser() == null)
                            //print on client's screen who is the loser and close game
                    //enabled by notify in setDicePositions, not needed in other invocations
                    match.setAction(0);
                }
                i++;
            }
            while (i>=1){
                enable(players.get(i-1));
                synchronized (Safe.actionL.get(IDMatch)) {
                    while (match.getAction() == 0)
                        try {
                            Safe.actionL.get(IDMatch).wait(sleepTime);
                            match.setAction(1);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    while (match.getLoser() == null)
                        //print on client's screen who is the loser and close game
                    //enabled by notify in setDicePositions, not needed in other invocations
                    match.setAction(0);
                }
                i--;
            }
            //players.shift();
            j++;
        }
    }
    //scoring phase then call a method each player giving the score
}
