package Logic;

import java.util.ArrayList;

public class Match {

    private ArrayList<Player> p;
    private Card[][] ToolCards;
    private Dice[][] RoundTrack;
    private Card[] PublicObjective;
    private PoolOfDices Pool;

    public Player getPlayers(){
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
