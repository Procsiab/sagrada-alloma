package shared.network;

import java.rmi.RemoteException;

public interface Router {
    Object route(Object callee, String mathod, Object[] args) throws RemoteException;
}