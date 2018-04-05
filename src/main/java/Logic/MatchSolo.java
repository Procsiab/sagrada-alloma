package Logic;

import java.util.ArrayList;

public class MatchSolo {
    private final Integer IDMatch;
    private final Player player;
    private Card[] toolCards;
    private Dice[][] roundTrack;
    private Card[] publicObjective;
    private PoolOfDices pool;
    private Integer action = 0;
    private Integer turno;
    private Player loser = null;

    public MatchSolo(Integer IDMatch, Player player){
        this.IDMatch = IDMatch;
        this.player = player;
    }

    public Player getPlayers(){
        return player;
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

    public void quit(){
        //declare all winners except who quit
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
