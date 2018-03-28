package Logic;

import java.util.Vector;

public class Match {

    private final Integer IDMatch;
    private Vector<Player> p;
    private Card[] ToolCards;
    private Dice[][] RoundTrack;
    private Card[] PublicObjective;
    private PoolOfDices Pool;


    Match(Integer IDMatch){
        this.IDMatch = IDMatch;
    }




    public Vector<Player> getPlayers(){
        return p;
    }

    public Card getToolCards(){
        return ToolCards;
    }

    public Card getPublicObjectives(){
        return PublicObjective;
    }

    public void setRoundTrack(Dice[][] d){

    }

    public Dice[][] getRoundTrack(){
        return RoundTrack;
    }


    public void turnManager(){

    }

    public Player getScoreboard(){

    }



}
