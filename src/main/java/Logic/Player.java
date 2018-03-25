package Logic;

public class Player {

    private Integer IDPlayer;
    private Integer IDMatch;
    private String Name;
    private Frame f;
    private Integer favorToken
    private Card privateObjective;

    Integer getScore();
    Integer getIdPlayer();
    Integer getIDMatch();
    String getName();
    Frame getFrame();
    void setFrame(Frame f);
    Integer getFavorToken();
    void setFavorToken(Integer i);
    Card getPrivateObjective();
    void setPrivateObjective(Card po);

}
