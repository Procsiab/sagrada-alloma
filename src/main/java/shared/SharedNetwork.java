package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Public interface to be shared with client and server,
// you should call remote methods in client from instances
// of this interface; it should extend Remote and have all
// the class' methods you want to be RMI

public interface SharedNetwork extends Remote {
    void connect(SharedMainClient c) throws RemoteException;
    String getServerIp() throws RemoteException;
}