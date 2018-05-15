package shared.network;

import server.threads.GameManager;
import shared.TransferObjects.GameManagerT;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SharedMiddlewareClient extends Remote {
    boolean deniedAccess() throws RemoteException;
    String startGame() throws RemoteException;
    void updateView(GameManagerT gameManager) throws RemoteException;
    boolean chooseWindow(ArrayList<Integer> windows) throws RemoteException;
    boolean ping() throws RemoteException;
    void aPrioriWin() throws RemoteException;
    void enable() throws RemoteException;
    void shut() throws RemoteException;
    void printScore(Integer score) throws RemoteException;
    void setWinner() throws RemoteException;
    boolean chooseWindowBack(Integer window) throws RemoteException;
    boolean startGameViewForced() throws RemoteException;
}