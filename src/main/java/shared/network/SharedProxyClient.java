package shared.network;

import shared.Cell;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * <h1>Shared Proxy Client</h1>
 * <p>This interface will be the client-side relay for receiving and making method calls from and to the server.
 * Methods marked as <strong>Local</strong> are called by the server's proxy, while methods marked as
 * <strong>Remote</strong> are called from the client towards the server.</p><br>
 * <p>This interface extends {@code Remote}, to allow sending over RMI the references of implementations of this interface;
 * thus, every method declared in it should also throw a {@link RemoteException}</p>
 * @see Remote
 * @see SharedProxyServer
 * @see client.ProxyClient
 * @see server.connection.ProxyServer
 */
public interface SharedProxyClient extends Remote {
    /**
     * <strong>Remote</strong><br>
     * First method that {@code SharedProxyClient} should call towards the server: it will register the client using its
     * nickname and other parameters based on the {@code Connection} used
     * @param nick text representing player's name; it's safe to obtain it from user's input
     * @return connection result message, could either be the handshake or a network issue message
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#startGame(String)
     */
    String startGame(String nick) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * Server calls this method on every player's client, on every turn, to send the match status as a {@link GameManagerT}.
     * The entire statistics of the player are inside the method's {@code gameManager} parameter, and should be used to
     * update the <b>view</b>
     * @param gameManager shared element between client and server which contains all match data, updated before the last
     *                    turn's start
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#updateView(GameManagerT)
     */
    void updateView(GameManagerT gameManager) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * After the players have been collected in a waiting room before the match begins, the server sends each player
     * four windows through this method; also, a matrix of {@link Cell} representing each window is sent, to allow
     * procedural printing of them
     * @param windows numbers of the four windows received (between 1 and 24)
     * @param matrices matrices of {@code Cell} representing each window layout
     * @return always returns {@code true} when the method call reaches the client
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#chooseWindow(ArrayList, ArrayList)
     */
    Boolean chooseWindow(ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * The server calls this method before calling each of the other local ones, to check the client's availability;
     * this method always returns {@code true}
     * @return always returns {@code true} when the method call reaches the client
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#ping()
     */
    Boolean ping() throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * If, according to the server, all other players have left, it calls this function on the client, ending the match
     * with your victory
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#aPrioriWin()
     */
    void aPrioriWin() throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * Before each turn starts, the server calls this method on the client which should begin playing
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#enable()
     */
    void enable() throws RemoteException;

    /**
     * <strong>Local</strong><br>
     *  Before each turn starts, the server calls this method on the clients which should not move during the next turn
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#shut()
     */
    void shut() throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * When a match has ended, the server calls this method on all players who played that match, providing the names,
     * score and winner flag for each player, as an {@code ArrayList}
     * @param nicks list of player nicknames, alphabetically ordered (from 1 to 4 players can have played a match)
     * @param scores list of player scores, ordered with respect to nicknames
     * @param winner list of boolean flags, ordered with respect to nicknames (multiple players may have the winner
     *               flag set, if they have the same final score)
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#printScore(ArrayList, ArrayList, ArrayList)
     */
    void printScore(ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * A client which has chosen the window through the <b>view</b> should call this method to tell the server about his
     * choice
     * @param window an {@code Integer} representing the chosen window; this must be contained in {@link SharedProxyClient#chooseWindow(ArrayList, ArrayList)}
     *               first parameter, which holds the list of available window numbers
     * @return a {@code Boolean} value representing the success of the call
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#chooseWindowBack(Integer)
     */
    Boolean chooseWindowBack(Integer window) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * If a player in the waiting room has not yet chosen its window and the timer for the room is up, server chooses
     * automatically a window for that player and then calls this method on it
     * @return always returns {@code true} when the method call reaches the client
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#startGameViewForced()
     */
    Boolean startGameViewForced() throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Whenever a player wants to pick a dice from the pool and place it in his window, this method should be called by
     * its client. Dices in the pool are addressed by an index between 1 and 9, and the position on the window is a {@link Position}
     * @param index index of the dice in the pool
     * @param p position of the destination position for thee selected dice on player's window
     * @return a {@code Boolean} value representing the success of the call
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#placeDice(Integer, Position)
     */
    Boolean placeDice(Integer index, Position p) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Whenever a player wants to use one of the tool cards, it must call this method, providing the correct parameters
     * for that tool card. If one or more parameters are not needed by the selected tool card, you just need to set them
     * to {@code null}; however, this method accepts every possible parameter that a tool card could need
     * @param i1 index of the selected tool card among the ones available from the {@code GameManagerT} that the server
     *           sent (an {@code Integer} between 1 and 12)
     * @param p1 first starting {@link Position} of a dice on the window
     * @param p2 first ending {@link Position} on the window
     * @param p3 second starting {@link Position} of a dice on the window
     * @param p4 second first ending {@link Position} on the window
     * @param pr dice {@link PositionR} in the round track
     * @param i2 dice index from the pool
     * @param i3 value to increment or decrement a dice
     * @return a {@code Boolean} value representing the success of the call
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#useToolC(Integer, Position, Position, Position, Position, PositionR, Integer, Integer)
     */
    Boolean useToolC(Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Whenever a player quits the match through an interaction with the <b>view</b>, this method should be called by its client
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#exitGame2()
     */
    void exitGame2() throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Whenever a players ends its turn through an interaction with the <b>view</b>, this method should be called by its client
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#endTurn()
     */
    void endTurn() throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * A player can ask the server to call the method {@link SharedProxyClient#updateView(GameManagerT)} on its client,
     * even if another player's turn has started
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#updateViewFromC()
     */
    void updateViewFromC() throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Whenever a player quits the waiting room through an interaction with the <b>view</b>, this method should be called by its client
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#exitGame1()
     */
    Boolean exitGame1() throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * TODO DESCRIPTION
     * @param s1
     * @param s2
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see client.ProxyClient#onTimeStatus(String, String)
     */
    void onTimeStatus(String s1, String s2) throws RemoteException;
}