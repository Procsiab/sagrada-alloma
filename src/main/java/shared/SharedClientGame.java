package shared;

import server.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface SharedClientGame extends Remote {

    void setWinner() throws RemoteException;
    void setNetGameManager(SharedServerGameManager gameManager);
    void chooseWindow(List<Integer> windows) throws RemoteException;
    void setNetPlayers(ArrayList<SharedServerPlayer> players) throws RemoteException;
    void setNPlayer(Integer n) throws RemoteException;
    void updateView() throws RemoteException;
    void enable() throws RemoteException;
    void shut() throws RemoteException;
    void aPrioriWin() throws RemoteException;
    boolean ping() throws RemoteException;
    void printScore(Integer score) throws RemoteException;
}