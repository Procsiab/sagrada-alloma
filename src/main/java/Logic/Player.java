package Logic;

public class Player {

    private final Integer IDPlayer;
    private final Integer IDMatch;
    private final String Name;
    private final Frame f;
    private Match match;
    private Integer favorToken
    private Card privateObjective;
    private Integer Turno;


    Player(Integer IDPlayer, Integer IDMatch, String Name, Frame f, Integer Turno){
        this.IDPlayer = IDPlayer;
        this.IDMatch = IDMatch;
        this.Name = Name;
        this.f = f;
        this.Turno = Turno;
    }


    public Match getMatch() {
        return match;
    }

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
