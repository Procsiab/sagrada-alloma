package shared.network;

import shared.SharedServerGameManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedMiddlewareServer extends Remote {
    public String startGame(String uuid, String ip, Integer port, Boolean isSocket) throws RemoteException;
    public void updateView(String uuid, SharedServerGameManager gameManager) throws RemoteException;
}