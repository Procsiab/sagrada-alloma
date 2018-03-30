package Logic;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Match {

    private final Integer IDMatch;
    private final LinkedList<Player> players;
    private Card[] ToolCards;
    private Dice[][] RoundTrack;
    private Card[] PublicObjective;
    private PoolOfDices Pool;
    private Integer action = 0;
    private Integer turno;
    Match(Integer IDMatch, LinkedList<Player> players){
        this.IDMatch = IDMatch;
        this.players = players;
    }

    public LinkedList<Player> getPlayers(){
        return players;
    }

    public Card[] getToolCards(){
        return ToolCards;
    }

    public Card[] getPublicObjectives(){
        return PublicObjective;
    }

    public void setRoundTrack(Dice[][] d){

    }


    public Dice[][] getRoundTrack(){
        return RoundTrack;
    }

    public void quit(){
        //declare all winners;
        notifyAll();
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
