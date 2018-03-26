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
    public Integer getIdPlayer();
    public Integer getIDMatch();
    public String getName();
    public Frame getFrame();
    public void setFrame(Frame f);
    public Integer getFavorToken();
    public void setFavorToken(Integer i);
    public Card getPrivateObjective();
    public void setPrivateObjective(Card po);
    public void setTurno(Frame f);
    public Integer getTurno();
}
