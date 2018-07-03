package server.connection;

import server.MatchManager;
import server.SReferences;
import server.threads.GameManager;
import shared.Cell;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.network.Connection;
import shared.network.MethodConnectionException;
import shared.network.SharedProxyServer;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.rmi.Remote;
import java.util.ArrayList;

/**
 * <p>This class implements {@code SharedProxyServer} and all its methods, making them interact with the correct <b>controller</b>
 * components (if they are local) or routing them with the correct parameters to the client (if they are remote)</p><br>
 * <p>This class implements the <strong>singleton</strong> design pattern, having just one instance of it available per JVM;
 * this instance can be accessed through the static class' methods.</p><br>
 * <p>This class holds both a {@code NetworkRmi} and a {@code NetworkSocket} static references, constructed as servers.</p>
 * <p>Finally, the private attribute {@code Boolean test} is used to determine whether this class should be used in a testing
 * environment: this means that some methods which try to use a connection will instead fallback to a mock</p>
 * @see SharedProxyServer
 * @see Connection
 */
public final class ProxyServer implements SharedProxyServer {
    private static final String SERVER_INTERFACE = "ProxyServer";

    private static Connection serverSocket = new NetworkSocket();
    private static Connection serverRmi = new NetworkRmi();
    private static ProxyServer instance = new ProxyServer();
    private static Boolean test = false;

    /**
     * Private constructor, prevents external access and uncontrolled instantiation of the class: use the static method
     * {@link ProxyServer#getInstance()} to obtain the reference to the internal instance
     */
    private ProxyServer() {
        super();
        NetworkRmi.remotize(this, serverRmi.getListeningPort());
        serverRmi.export(this, SERVER_INTERFACE);
        serverSocket.export(this, SERVER_INTERFACE);
    }

    /**
     * Obtain a reference to the class' instance
     * @return always the same instance, saved as a {@code private static} reference in the class
     */
    public static ProxyServer getInstance() {
        return instance;
    }

    /**
     * Getter method to obtain the saved {@code Connection} attribute which holds teh {@code NetworkSocket} instance
     * @return internal class' socket connection, if set; otherwise returns {@code null}
     */
    public static Connection getServerSocket() {
        return serverSocket;
    }

    /**
     * Getter method to obtain the saved {@code Connection} attribute which holds teh {@code NetworkRmi} instance
     * @return internal class' RMI connection, if set; otherwise returns {@code null}
     */
    public static Connection getServerRmi() {
        return serverRmi;
    }

    /**
     * Setter method to enable the "test mode" for this class, assigning {@code true} to the attribute
     */
    public static void setTest(){
        test = true;
    }

    /**true
     * <strong>Remote</strong><br>
     * This method will choose the {@code Connection} attribute, among the Socket or RMI one, to call a method on the
     * client with; since the player communicates its connection preferences when it calls {@link SharedProxyServer#startGame(String, String, String, Integer, Boolean, Remote)}
     * the server will register it in an {@link SReferences} class instance, allowing any object on the server to use
     * static getters to access those data.<br>
     * If the player connected using RMI, the method call is performed straightforward using {@link Connection#invokeMethod(String, String, Object[])};
     * however, if the player connected using Socket, it is the necessary to connect the server to the player's client
     * with a new {@link NetworkSocket} instance, constructed on the player's IP and listening port
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param methodName text representing the method name, should be the same as in the signature
     * @param args array of object representing the method's parameters, in the exact order as in the signature
     * @return the return value from the method invocation (if the invoked method was void, it will be {@code null})
     * @see SReferences#getIsSocketRef(String)
     * @see SReferences#getIpRef(String)
     * @see SReferences#getPortRef(String)
     */
    private Object forwardMethod(String uuid, String methodName, Object[] args) {
        boolean useSocket = false;
        try {
            useSocket = SReferences.getIsSocketRef(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID: " + uuid);
        }
        if (useSocket) {
            Connection client = null;
            try {
                client = new NetworkSocket(SReferences.getIpRef(uuid), SReferences.getPortRef(uuid));
                return client.invokeMethod(uuid, methodName, args);
            } catch (MethodConnectionException mce) {
                Logger.log(SReferences.getGameRef(uuid) + " player " + uuid + " socket error occurred while invoking method " + methodName);
            } finally {
                if (client != null) {
                    client.close();
                }
            }
        } else {
            try {
                return serverRmi.invokeMethod(uuid, methodName, args);
            } catch (MethodConnectionException mce) {
                Logger.log(SReferences.getGameRef(uuid) + " player " + uuid + " RMI error occurred while invoking method " + methodName);
            }
        }
        return null;
    }

    /**
     * <strong>Local</strong><br>
     * Whenever a client calls a method on the server, this method should be called first, to check if the client involved
     * in the call has been authorized to call methods (e.g. a player will not have such authorization unless during its turn)
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @return boolean value representing the <b>denial</b> of the authorization: it will be {@code true} if the player
     *         with UUID {@code uuid} has no authorization
     */
    private Boolean deniedAccess(String uuid) {
        try {
            GameManager game = SReferences.getGameRef(uuid);
            String expected = game.getExpected();

            if (expected.equals("all"))
                return false;
            else if (expected.equals("none") || !expected.equals(uuid)) {
                Logger.log("Denied access for UUID " + uuid);
                return true;
            }
            return false;
        } catch (NullPointerException npe) {
            return true;
        }
    }

    /**
     * <strong>Local</strong><br>
     * This method is called whenever a player asks to register on the server; moreover, if that player connected using
     * RMI, its remote reference (passed as the {@code stub} parameter) is exported on the registry. The player is then
     * registered binding all its configuration parameters to its Unique User ID
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param nick {@code String}
     * @param ip {@code String}
     * @param port {@code Integer}
     * @param isSocket {@code Boolean}
     * @param stub {@code Remote}
     * @return server's response to the client's registration request
     * @see SharedProxyServer#startGame(String, String, String, Integer, Boolean, Remote)
     */
    @Override
    public String startGame(String uuid, String nick, String ip, Integer port, Boolean isSocket, Remote stub) {
        if (!isSocket) {
            // Export given stub to let the server call the client's methods
            serverRmi.export(stub, uuid);
        }
        return MatchManager.startGame(uuid, nick, ip, port, isSocket);
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param gameManager {@link GameManagerT}
     * @see shared.network.SharedProxyServer#updateView(String, GameManagerT)
     */
    @Override
    public void updateView(String uuid, GameManagerT gameManager) {
        forwardMethod(uuid, "updateView", new Object[]{gameManager});
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param windows {@code ArrayList<Integer>}
     * @param matrices {@code ArrayList<{@link Cell}[][]>}
     * @return {@code Boolean}
     * @see shared.network.SharedProxyServer#chooseWindow(String, ArrayList, ArrayList)
     */
    @Override
    public Boolean chooseWindow(String uuid, ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
        Boolean ret = (Boolean) forwardMethod(uuid, "chooseWindow", new Object[]{windows, matrices});
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    /**
     * <strong>Remote</strong><br>
     * If the attribute {@code test} is true, this method will just return {@code true} without performing a connection
     * to the client, assuming that it will always succeed
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @return {@code Boolean}
     * @see shared.network.SharedProxyServer#ping(String)
     */
    @Override
    public Boolean ping(String uuid) {
        if(test) {
            return true;
        } else {
            Boolean ret = (Boolean) forwardMethod(uuid, "ping", null);
            if (ret != null) {
                return ret;
            } else {
                return false;
            }
        }
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @see shared.network.SharedProxyServer#tavoloWin(String)
     */
    @Override
    public void tavoloWin(String uuid) {
        forwardMethod(uuid, "tavoloWin", null);
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @see shared.network.SharedProxyServer#enable(String)
     */
    @Override
    public void enable(String uuid) {
        forwardMethod(uuid, "enable", null);
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @see shared.network.SharedProxyServer#shut(String)
     */
    @Override
    public void shut(String uuid) {
        forwardMethod(uuid, "shut", null);
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @see shared.network.SharedProxyServer#printScore(String, ArrayList, ArrayList, ArrayList)
     */
    @Override
    public void printScore(String uuid, ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) {
        forwardMethod(uuid, "printScore", new Object[]{nicks, scores, winner});
    }

    /**
     * <strong>Local</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param window {@code Integer}
     * @return {@code Boolean}
     * @see shared.network.SharedProxyServer#chooseWindow(String, ArrayList, ArrayList)
     */
    @Override
    public Boolean chooseWindowBack(String uuid, Integer window) {
        try {
            if (deniedAccess(uuid))
                return false;
            return SReferences.getPlayerRef(uuid).setWindowFromC(window);
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @return {@code Boolean}
     * @see shared.network.SharedProxyServer#startGameViewForced(String)
     */
    @Override
    public Boolean startGameViewForced(String uuid) {
        Boolean ret = (Boolean) forwardMethod(uuid, "startGameViewForced", null);
        if (ret != null) {
            return ret;
        } else {
            return false;
        }
    }

    /**
     * <strong>Local</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param index {@code Integer}
     * @param p {@link Position}
     * @return {@code Boolean}
     * @see shared.network.SharedProxyServer#placeDice(String, Integer, Position)
     */
    @Override
    public Boolean placeDice(String uuid, Integer index, Position p) {
        try {
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            if (deniedAccess(uuid)) {
                game.getThreads().decrementAndGet();
                return false;
            }
            Boolean value;
            value = SReferences.getPlayerRef(uuid).placeDice(index, p);
            game.getThreads().decrementAndGet();
            return value;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * <strong>Local</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param i1 {@code Integer}
     * @param p1 {@link Position}
     * @param p2 {@link Position}
     * @param p3 {@link Position}
     * @param p4 {@link Position}
     * @param pr {@link PositionR}
     * @param i2 {@code Integer}
     * @param i3 {@code Integer}
     * @return {@code Boolean}
     * @see shared.network.SharedProxyServer#useToolC(String, Integer, Position, Position, Position, Position, PositionR, Integer, Integer)
     */
    @Override
    public Boolean useToolC(String uuid, Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        try {
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            if (deniedAccess(uuid)) {
                game.getThreads().decrementAndGet();
                return false;
            }
            Boolean value;
            value = SReferences.getPlayerRef(uuid).useTool(i1, p1, p2, p3, p4, pr, i2, i3);
            game.getThreads().decrementAndGet();
            return value;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * <strong>Local</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @see shared.network.SharedProxyServer#exitGame2(String)
     */
    @Override
    public void exitGame2(String uuid) {
        try {
            SReferences.getGameRef(uuid).exitGame2(uuid);
        } catch (NullPointerException npe) {
            //Logger.log("Player " + uuid + " has disconnected");
        }
    }

    /**
     * <strong>Local</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @see shared.network.SharedProxyServer#endTurn(String)
     */
    @Override
    public void endTurn(String uuid) {
        try {
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            if (deniedAccess(uuid)) {
                game.getThreads().decrementAndGet();
                return;
            }
            SReferences.getGameRef(uuid).endTurn();
            game.getThreads().decrementAndGet();
        } catch (NullPointerException npe) {
            Logger.log("Unable to terminate turn for player with UUID " + uuid);
        }
    }

    /**
     * <strong>Local</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @see shared.network.SharedProxyServer#updateViewFromC(String)
     */
    @Override
    public void updateViewFromC(String uuid) {
        try {
            GameManager game = SReferences.getGameRef(uuid);
            game.getThreads().incrementAndGet();
            if (deniedAccess(uuid)) {
                game.getThreads().decrementAndGet();
                return;
            }
            SReferences.getPlayerRef(uuid).updateViewFromC(uuid);
            game.getThreads().decrementAndGet();
        } catch (NullPointerException npe) {
            Logger.log("Unable to update teh view of player with UUID " + uuid);
        }
    }

    /**
     * <strong>Local</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @return {@code Boolean}
     * @see shared.network.SharedProxyServer#exitGame1(String)
     */
    @Override
    public Boolean exitGame1(String uuid) {
        try {
            return MatchManager.exitGame1(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to log out player with UUID " + uuid);
        }
        return false;
    }

    /**
     * <strong>Remote</strong><br>
     * @param uuid see {@link shared.network.SharedProxyClient} for more about the first parameter
     * @param s1 {@code String}
     * @param s2 {@code String}
     * @see shared.network.SharedProxyServer#onTimeStatus(String, String, String)
     */
    @Override
    public void onTimeStatus(String uuid, String s1, String s2) {
        forwardMethod(uuid, "onTimeStatus", new Object[]{s1, s2});
    }
}