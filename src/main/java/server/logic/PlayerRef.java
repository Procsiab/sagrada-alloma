package server.logic;

public class PlayerRef {
    Integer idPlayer;
    String name;
    Integer nMates;

    public PlayerRef(Integer idPlayer, String name, Integer nMates){
        this.idPlayer = idPlayer;
        this.name = name;
        this.nMates = nMates;
    }


    public String getName() {
        return name;
    }

    public Integer getID() {
        return idPlayer;
    }

    public Integer getnMates() {

        return nMates;
    }
}
