package shared.network;

import shared.SharedServerGameManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedMiddlewareClient extends Remote {
    public String startGame(String uuid) throws RemoteException;
    public void updateView(SharedServerGameManager gameManager) throws RemoteException;

}
