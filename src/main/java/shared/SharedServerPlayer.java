package shared;

import server.Dice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedServerPlayer extends Remote {
    void setWindow(Integer n) throws RemoteException;
    boolean placeDice(Dice dice, Position position) throws RemoteException;
    void setPrivateOC(Integer k) throws RemoteException;
    void setFrame(Integer k) throws RemoteException;
    void setTokens() throws RemoteException;
}
