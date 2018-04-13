package shared;

import java.rmi.RemoteException;

public interface SharedServerMatchManager {
    String startGame(SharedClientGame client, Integer nMates) throws RemoteException;
}
