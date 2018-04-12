package server.logic;

import java.util.ArrayList;
import java.util.List;

public class Match {

    public final Integer idMatch;
    public final List<Player> players;
    public Card[] toolCards;
    public Dice[][] roundTrack;
    public Card[] publicObjective;
    public Locker safe = Locker.getSafe();
    public Integer action = 0;
    public Integer turno;
    public Player loser = null;
    public Integer toDelete = 0;

    public Match(Integer idMatch, List<Player> players){
        this.idMatch = idMatch;
        this.players = players;
    }

    public List<Player> getPlayers(){
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

    public Integer getToDelete() {
        return toDelete;
    }

    public Dice[][] getRoundTrack(){
        return roundTrack;
    }

    public void quit(Integer idPlayer){
        synchronized (safe.allQPPMA) {
            //declare all winners except who quit
        }
    }

    public void listen(Player p){
        //send enable signal to player p and shut all others
    }


    public Integer getIdMatch() {
        return idMatch;
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
