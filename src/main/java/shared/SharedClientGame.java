package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedClientGame extends Remote {
    void print(String string);
}