package shared;

import server.Dice;
import server.Position;

public interface SharedServerPlayer {
    void setWindows(Integer n);
    boolean placeDice(Dice dice, Position position);
}
