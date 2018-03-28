package Logic;

import java.util.Vector;

public class Match {

    private final Integer IDMatch;
    private Integer nParticipants;
    private Integer action = 0;
    private Vector<Player> p;
    private Card[] ToolCards;
    private Dice[][] RoundTrack;
    private Card[] PublicObjective;
    private PoolOfDices Pool;
    private Integer Turno;


    Match(Integer IDMatch){
        this.IDMatch = IDMatch;
        this.Turno = 1;
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

    public void quit(){
        //declare all winners;
        notifyAll();
    }

    public void listen(Player p){
        //send enable signal to player p and shut all others
    }

    public void turnManager(){
        int i = 1;
        for(Turno = 1; Turno<=10; Turno++){
            while(i<= nParticipants){
                listen(p.get(i-1));
                while(action == 0)
                    this.wait();
                    //enabled by notify in setDicePositions
                action = 0;
                i++;
            }
            while (i>=1){
                listen(p.get(i-1));
                while(action == 0)
                    this.wait();
                    //enabled by notify in setDicePositions
                action = 0;
                i--;
            }
        }
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

    }



}
