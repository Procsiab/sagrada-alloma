package Network.Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

// Public interface to be shared with client and server,
// you should call remote methods in client from instances
// of this interface; it should extend Remote and have all
// the class' methods you want to be RMI

public interface SharedNetwork extends Remote {
    String getServerIp() throws RemoteException;
    String getPlayerName(Integer id) throws RemoteException;
    String getPlayerRefList() throws RemoteException;
}