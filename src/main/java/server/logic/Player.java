package server.logic;

public class Player {

    private final Integer idPlayer;
    private final Integer idMatch;
    private final String name;
    private Frame frame;
    private Integer favorToken;
    private Card privateObjective;
    private Integer toDelete = 0;
    private Integer turno = 1;

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
