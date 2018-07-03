package shared.network;

import shared.Cell;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * <p>This interface will be the server-side relay for receiving and making method calls from and to the client.
 * Methods marked as <strong>Local</strong> are called by the client's proxy, while methods marked as
 * <strong>Remote</strong> are called from the server towards the client.</p><br>
 * <p>This interface extends {@code Remote}, to allow sending over RMI the references of implementations of this interface;
 * thus, every method declared in it should also throw a {@link RemoteException}</p><br>
 * <p>Since this is the server's proxy, every method signature will have a {@code String uuid} as first parameter, to
 * identify the client on which the method should be called or the client which has called the method</p>
 * @see Remote
 * @see SharedProxyClient
 * @see server.connection.ProxyServer
 * @see client.ProxyClient
 */
public interface SharedProxyServer extends Remote {

    /**
     * <strong>Local</strong><br>
     * First method that {@code SharedProxyClient} should call towards the server: it will perform the registration of
     * the player on the server, checking if he has already logged in or he was playing (and in case the player will join
     * again his suspended match). If socket RMI connection is being used, the parameter {@code Remote stub} is passed after
     * {@link shared.network.rmi.NetworkRmi#remotize(Object, Integer)} was called on its reference; otherwise it will be null
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @param nick {@code String}
     * @param ip {@code Integer}
     * @param port {@code Integer}
     * @param isSocket {@code Boolean}
     * @param stub {@code Object}
     * @return string of text representing the server response to the client's registration request
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see SharedProxyServer#startGame(String, String, String, Integer, Boolean, Remote)
     */
    String startGame(String uuid, String nick, String ip, Integer port, Boolean isSocket, Remote stub) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Server calls this method on every player's client, on every turn, to send the match status as a {@link GameManagerT}.
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @param gameManager shared element between client and server which contains all match data, updated before the last
     *                    turn's start
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#updateView(String, GameManagerT)
     */
    void updateView(String uuid, GameManagerT gameManager) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * After the players have been collected in a waiting room before the match begins, the server sends each player
     * four windows through this method; also, a matrix of {@link Cell} representing each window is sent, to allow
     * procedural printing of them
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @param windows numbers of the four windows received (between 1 and 24)
     * @param matrices matrices of {@code Cell} representing each window layout
     * @return a {@code Boolean} value representing the success of the call
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#chooseWindow(String, ArrayList, ArrayList)
     */
    Boolean chooseWindow(String uuid, ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * This method should be called before each of the other remote ones, to check the client's availability
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @return a {@code Boolean} value representing the success of the call
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#ping(String)
     */
    Boolean ping(String uuid) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * If all other players have left, this method is called on the only client left, ending the match
     * with its victory
     * @param uuid see {@link SharedProxyClient} for more about the first parameter
     * @throws RemoteException see {@link SharedProxyClient} for more about this throw
     * @see server.connection.ProxyServer#tavoloWin(String)
     */
    void tavoloWin(String uuid) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Before each turn starts this method should be called on the client which should begin playing in the next turn
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#enable(String)
     */
    void enable(String uuid) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Before each turn starts this method should be called on the clients which should not move during the next turn
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#shut(String)
     */
    void shut(String uuid) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * When a match ends, the server calls this method on all players who played that match, providing the names,
     * score and winner flag for each player, as an {@code ArrayList}
     * @param nicks list of player nicknames, alphabetically ordered (from 1 to 4 players can have played a match)
     * @param scores list of player scores, ordered with respect to nicknames
     * @param winner list of boolean flags, ordered with respect to nicknames (multiple players may have the winner
     *               flag set, if they have the same final score)
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#printScore(String, ArrayList, ArrayList, ArrayList)
     */
    void printScore(String uuid, ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * This method is called by a client which has chosen the desired window, while being held in the waiting room
     * @param window an {@code Integer} representing the chosen window; this must be contained in {@link SharedProxyServer#chooseWindow(String, ArrayList, ArrayList)}
     *               second parameter, which holds the list of available window numbers
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @return always returns {@code true} when the method call reaches the server
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#chooseWindowBack(String, Integer)
     */
    Boolean chooseWindowBack(String uuid, Integer window) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * If a player in the waiting room has not yet chosen its window and the timer for the room is up, this method is
     * called on the player; also a window index is automatically chosen by the <b>controller</b>, for that player. It
     * will be notified about his window when {@link SharedProxyServer#updateView(String, GameManagerT)} is called on it
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @return a {@code Boolean} value representing the success of the call
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#startGameViewForced(String)
     */
    Boolean startGameViewForced(String uuid) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * This method should be called whenever a player wants to pick a dice from the pool and place it in his window.
     * Dices in the pool are addressed by an index between 1 and 9, and the position on the window is a {@link Position}
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @param index index of the dice in the pool
     * @param p position of the destination position for thee selected dice on player's window
     * @return always returns {@code true} when the method call reaches the server
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#placeDice(String, Integer, Position)
     */
    Boolean placeDice(String uuid, Integer index, Position p) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * This method should be called whenever a player wants to use one of the tool cards providing the correct parameters
     * for that tool card. If one or more parameters are not needed by the selected tool card, they should be set to
     * {@code null}; however, this method accepts every possible parameter that a tool card could need
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @param i1 index of the selected tool card among the ones available from the {@code GameManagerT} that the server
     *           sent (an {@code Integer} between 1 and 12)
     * @param p1 first starting {@link Position} of a dice on the window
     * @param p2 first ending {@link Position} on the window
     * @param p3 second starting {@link Position} of a dice on the window
     * @param p4 second first ending {@link Position} on the window
     * @param pr dice {@link PositionR} in the round track
     * @param i2 dice index from the pool
     * @param i3 value to increment or decrement a dice
     * @return always returns {@code true} when the method call reaches the server
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#useToolC(String, Integer, Position, Position, Position, Position, PositionR, Integer, Integer)
     */
    Boolean useToolC(String uuid, Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * This method should be called whenever a player quits the match through an interaction with the <b>view</b>
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#exitGame2(String)
     */
    void exitGame2(String uuid) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * This method is called whenever a players ends its turn through an interaction with the <b>view</b>
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#endTurn(String)
     */
    void endTurn(String uuid) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * This method is called when a player asks the server to call the method {@link SharedProxyClient#updateView(GameManagerT)}
     * on its client even if another player's turn has started
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#updateViewFromC(String)
     */
    void updateViewFromC(String uuid) throws RemoteException;

    /**
     * <strong>Local</strong><br>
     * This method is called whenever a player quits the waiting room through an interaction with its <b>view</b>
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @return always returns {@code true} when the method call reaches the server
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#exitGame1(String)
     */
    Boolean exitGame1(String uuid) throws RemoteException;

    /**
     * <strong>Remote</strong><br>
     * Whenever the connection status of a player changes, this method should be called the other active players,
     * in the same match; only one of the arguments will be passed at a time
     * @param uuid see {@link SharedProxyServer} for more about the first parameter
     * @param s1 will contain the nick name of the player who reconnected (in this case {@code s2} will be {@code null})
     * @param s2 will contain the nick name of the player who disconnected (in this case {@code s1} will be {@code null})
     * @throws RemoteException see {@link SharedProxyServer} for more about this throw
     * @see server.connection.ProxyServer#onTimeStatus(String, String, String)
     */
    void onTimeStatus(String uuid, String s1, String s2) throws RemoteException;
}