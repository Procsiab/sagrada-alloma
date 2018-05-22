package shared.network;

import shared.Dice;
import shared.Position;
import shared.TransferObjects.GameManagerT;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SharedMiddlewareClient extends Remote {
    Boolean deniedAccess() throws RemoteException;
    String startGame() throws RemoteException;
    void updateView(GameManagerT gameManager) throws RemoteException;
    Boolean chooseWindow(ArrayList<Integer> windows) throws RemoteException;
    Boolean ping() throws RemoteException;
    void aPrioriWin() throws RemoteException;
    void enable() throws RemoteException;
    void shut() throws RemoteException;
    void printScore(Integer score) throws RemoteException;
    void setWinner() throws RemoteException;
    Boolean chooseWindowBack(Integer window) throws RemoteException;
    Boolean startGameViewForced() throws RemoteException;
    Boolean placeDice(Dice d, Position p) throws RemoteException;
}