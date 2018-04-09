package ServerP2P.Logic;

public class PlayerRef {
    Integer IDPlayer;
    String Name;
    Integer nMates;

    public PlayerRef(Integer IDPlayer, String Name, Integer nMates){
        this.IDPlayer = IDPlayer;
        this.Name = Name;
        this.nMates = nMates;
    }


    public String getName() {
        return Name;
    }

    public Integer getID() {
        return IDPlayer;
    }

    public Integer getnMates() {

        return nMates;
    }
}
