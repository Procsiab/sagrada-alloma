package shared;

import server.Dice;
import server.Position;

import java.rmi.Remote;

public interface SharedServerPlayer extends Remote {
    void setWindow(Integer n);
    boolean placeDice(Dice dice, Position position);
}
