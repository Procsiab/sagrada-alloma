package Logic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Match {

    private final Integer IDMatch;
    private final ArrayList<Player> players;
    private Card[] toolCards;
    private Dice[][] roundTrack;
    private Card[] publicObjective;
    private Locker Safe = Locker.getSafe();
    private Integer action = 0;
    private Integer turno;
    private Player loser = null;

    public Match(Integer IDMatch, ArrayList<Player> players){
        this.IDMatch = IDMatch;
        this.players = players;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Card[] getToolCards(){
        return toolCards;
    }

    public Card[] getPublicObjectives(){
        return publicObjective;
    }

    public Player getLoser() {
        return loser;
    }

    public void setRoundTrack(Dice[][] d){

    }


    public Dice[][] getRoundTrack(){
        return roundTrack;
    }

    public void quit(Integer IDPlayer){
        synchronized (Safe.allQPPMA) {
            //declare all winners except who quit
        }
    }

    public void listen(Player p){
        //send enable signal to player p and shut all others
    }


    public Integer getIDMatch() {
        return IDMatch;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Player getScoreboard(){
        return null;
    }
}
