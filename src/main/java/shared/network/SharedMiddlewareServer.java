package shared.network;

import shared.GameManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SharedMiddlewareServer extends Remote {
    boolean deniedAccess(String uuid) throws RemoteException;
    String startGame(String uuid, String ip, Integer port, Boolean isSocket) throws RemoteException;
    void updateView(String uuid, GameManager gameManager) throws RemoteException;
    Integer chooseWindow(String uuid, ArrayList<Integer> windows) throws RemoteException;
    boolean ping(String uuid) throws RemoteException;
    void aPrioriWin(String uuid) throws RemoteException;
    void enable(String uuid) throws RemoteException;
    void shut(String uuid) throws RemoteException;
    void printScore(String uuid, Integer score) throws RemoteException;
    void setWinner(String uuid) throws RemoteException;
}