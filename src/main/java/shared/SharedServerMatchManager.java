package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Public interface to be shared with client and server,
// you should call remote methods in client from instances
// of this interface; it should extend Remote and have all
// the class' methods you want to be RMI

public interface SharedServerMatchManager extends Remote {
    String startGame(SharedClientGame client) throws RemoteException;
    public boolean exitGame1(SharedClientGame client)throws RemoteException;
}