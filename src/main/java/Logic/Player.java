package Logic;

public class Player {

    private Integer IDPlayer;
    private Integer IDMatch;
    private String Name;
    private Frame f;
    private Integer favorToken
    private Card privateObjective;
    private Integer Turno;

    public Integer getScore();

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

    public Integer getTurno() {
        return Turno;
    }
}
