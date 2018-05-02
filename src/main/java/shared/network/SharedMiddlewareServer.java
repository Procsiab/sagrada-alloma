package shared.network;

import server.abstracts.Window;
import server.threads.GameManager;
import shared.SharedServerGameManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SharedMiddlewareServer extends Remote {
    String startGame(String uuid, String ip, Integer port, Boolean isSocket) throws RemoteException;
    void updateView(String uuid, SharedServerGameManager gameManager) throws RemoteException;
    void setSGame(String uuid, GameManager gameManager) throws RemoteException;
    Integer chooseWindow(String uuid, ArrayList<Integer> windows) throws RemoteException;

}