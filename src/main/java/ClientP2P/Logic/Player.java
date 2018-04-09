package ClientP2P.Logic;

public class Player {

    private final Integer IDPlayer;
    private final Integer IDMatch;
    private final String Name;
    private Frame f;
    private Integer favorToken;
    private Card privateObjective;
    private Integer toDelete = 0;
    private Integer turno = 1;

    public Player(Integer IDPlayer, Integer IDMatch, String Name){
        this.IDPlayer = IDPlayer;
        this.IDMatch = IDMatch;
        this.Name = Name;
    }



    public Integer getScore() { return 0; }

    public Integer getIDPlayer() {
        return IDPlayer;
    }

    public Integer getIDMatch() {
        return IDMatch;
    }

    public String getName() {
        return Name;
    }

    public void setFrame(Frame f){

    }

    public Frame getF() {
        return f;
    }

    public void setFavorToken(Integer i){

    }

    public Integer getFavorToken() {
        return favorToken;
    }

    public void setPrivateObjective(Card po){

    }

    public Card getPrivateObjective() {
        return privateObjective;
    }

    public void setTurno(Frame f){

    }

    public Integer getToDelete() {
        return toDelete;
    }

    public Integer getTurno() {
        return turno;
    }
}
