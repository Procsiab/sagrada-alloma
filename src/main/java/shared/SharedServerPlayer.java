package shared;

import server.Dice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedServerPlayer extends Remote {

    SharedClientGame getClientGame() throws RemoteException;
    void setClientGame(SharedClientGame sharedClientGame) throws RemoteException;
    Integer getScore() throws RemoteException;
    void setScore(Integer n) throws RemoteException;
    void setWindow(Integer n) throws RemoteException;
    boolean placeDice(Dice dice, Position position) throws RemoteException;
    void setPrivateOC(Integer k) throws RemoteException;
    void setFrame(Integer k) throws RemoteException;
    void setTokens() throws RemoteException;
}
