package server.threads;

import server.logic.Locker;
import server.logic.Match;
import server.logic.Player;

import java.util.ArrayList;
import java.util.List;

public class TurnManager extends GeneralTask {

    public final Integer idMatch;
    public final Match match;
    public final List<Player> players;
    public final Integer sleepTime;

    public Locker safe = Locker.getSafe();

    public TurnManager(Integer idMatch, Match match, List<Player> players){
        this.idMatch = idMatch;
        this.match = match;
        this.players = players;
        this.sleepTime = 10000;
    }




    public void enable(Player player){
        //enable selected player, by invoking method on the client-side. Shut all others.
    }



    @Override
    public void run() {

        //show shuffle public Objective Card animation on all clients
        //show 4 public objective cards and 1 window frame player board. Player will choose only 1 card.
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
                synchronized (safe.actionL.get(idMatch)) {
                    while (match.getAction() == 0)
                        try {
                            safe.actionL.get(idMatch).wait(sleepTime);
                            match.setAction(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    while (match.getLoser() == null) {
                        //print on client's screen who is the loser and close game
                        //enabled with passaturno called from client, not needed in other invocations
                    }
                    match.setAction(0);
                }
                i++;
            }
            while (i>=1){
                enable(players.get(i-1));
                synchronized (safe.actionL.get(idMatch)) {
                    while (match.getAction() == 0)
                        try {
                            safe.actionL.get(idMatch).wait(sleepTime);
                            match.setAction(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    while (match.getLoser() == null) {
                        //print on client's screen who is the loser and close game
                        //enabled with passaturno called from client, not needed in other invocations
                    }
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
