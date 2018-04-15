package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedClientGame extends Remote {
    String getClientIp() throws RemoteException;
    void print(String s) throws RemoteException;
}