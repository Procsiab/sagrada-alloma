package shared;

import server.Player;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

public interface SharedClientGame extends Remote {
    void print(String string);
    void chooseWindow(List<Integer> windows);
    void setNetPlayers(ArrayList<Player> players);
    void setNPlayer(Integer n);
    void updateView();
    void enable();
    void shut();
    void aPrioriWin();
    boolean ping();
    void score(Integer score);
}