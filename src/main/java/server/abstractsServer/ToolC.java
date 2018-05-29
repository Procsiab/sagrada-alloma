package server.abstractsServer;

import server.Player;
import server.threads.GameManager;
import shared.Position;
import shared.PositionR;

import java.io.*;

public abstract class ToolC implements Serializable {

    private Integer tokensRequired = 1;
    private String name;
    private String description;

    public abstract boolean ableAndSettle(Player player);

    //p1 is the original position of dice 1
    //p2 is the original position on dice 2
    //p3 is allegedly the new position of dice 1
    //p4 is allegedly the new position of dice 2
    //pr is the position of dice on the RoundTrack
    //i2 is the position of dice in the dicePool
    //i3 is the number to add to the selected dice, or the value to set to the selected dice
    public abstract boolean use(GameManager game, Player player, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3);

    public ToolC deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (ToolC) ois.readObject();
        } catch (IOException|ClassNotFoundException e) {
            return null;
        }
    }

    public Integer getTokensRequired(){
        return this.tokensRequired;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTokensRequired(Integer tokensRequired) {
        this.tokensRequired = tokensRequired;
    }
}