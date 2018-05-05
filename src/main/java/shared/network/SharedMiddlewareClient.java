package shared.network;

import shared.SharedServerGameManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SharedMiddlewareClient extends Remote {
    String startGame(String uuid) throws RemoteException;
    void updateView(SharedServerGameManager gameManager) throws RemoteException;
    Integer chooseWindow(ArrayList<Integer> windows) throws RemoteException;
    boolean ping() throws RemoteException;
    void aPrioriWin() throws RemoteException;
    void enable() throws RemoteException;
    void shut() throws RemoteException;
    void printScore(Integer score) throws RemoteException;
    void setWinner() throws RemoteException;
}