package client.logic;

public class Player {

    public final Integer idPlayer;
    public final Integer idMatch;
    public final String name;
    public Frame frame;
    public Integer favorToken;
    public Card publicObjective;
    public Integer toDelete = 0;
    public Integer turno = 1;

    public Player(Integer idPlayer, Integer idMatch, String name){
        this.idPlayer = idPlayer;
        this.idMatch = idMatch;
        this.name = name;
    }



    public Integer getScore() { return 0; }

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public Integer getIdMatch() {
        return idMatch;
    }

    public String getName() {
        return name;
    }

    public void setFrame(Frame f){

    }

    public Frame getFrame() {
        return frame;
    }

    public void setFavorToken(Integer i){

    }

    public Integer getFavorToken() {
        return favorToken;
    }

    public void setpublicObjective(Card po){

    }

    public Card getpublicObjective() {
        return publicObjective;
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
