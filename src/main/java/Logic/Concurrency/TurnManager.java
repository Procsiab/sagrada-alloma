package Logic.Concurrency;

import Logic.Locker;
import Logic.Match;
import Logic.Player;

import java.util.LinkedList;

public class TurnManager extends GeneralTask {

    private Integer IDPlayer;
    private Match match;
    private LinkedList<Player> players;

    private Locker Safe = Locker.getSafe();

    public TurnManager(){

    }




    public void listen(Player player){
        //enable selected player
    }



    @Override
    public void run() {
        int j = 1;
        int i = 1;
        while(j<=10){
            while(i<= players.size()){
                listen(players.get(i-1));
                synchronized (Safe.action) {
                    while (match.getAction() == 0)
                        try{
                        this.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                    //enabled by notify in setDicePositions, not needed in other invocations
                    match.setAction(0);
                }
                i++;
            }
            while (i>=1){
                listen(players.get(i-1));
                synchronized (Safe.action) {
                    while (match.getAction() == 0)
                        try {
                            this.wait();
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
