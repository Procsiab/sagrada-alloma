package shared.network.socket;

import shared.Logger;
import shared.network.Connection;
import shared.network.MethodConnectionException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * <h1>Network Socket</h1>
 * <p>This class implements {@code Connection} and all its methods, using the Socket networking to set up the connection
 * components.</p><br>
 * <p>Using the constructor with no parameters or just an {@code Integer} representing the socket port, the instance will
 * act as a server, running the {@link NetworkSocket#startConsumer(Integer)} method in the constructor;</p>
 * <p>using the constructor with only a {@code String} parameter representing the server's hostname, or also with an
 * {@code Integer} parameter representing the server's inbound port, the instance will act as a client, trying to connect
 * to the specified hostname, running the {@link NetworkSocket#startProducer(String, Integer)} method in teh constructor</p><br>
 * <p>To make an object available to another host, this class keeps in a static {@link HashMap} with a {@code String}
 * as key: clients who want to access the object or call methods on it should send an {@link MethodRequestPacket} or an
 * {@link ObjectRequestPacket} to this host; moreover, the requested object should be exported using
 * {@link NetworkSocket#export(Object, String)}, which will also run {@link NetworkSocket#startConsumer(Integer)} if the
 * instance of this class was constructed as a client.</p><br>
 * <p>Finally, incoming requests are accepted by a secondary thread, which will be held id the private attribute
 * {@code Thread threadConsumer}: it will be run in the {@code startConsumer} method, and it is an instance of the class
 * {@link SocketServer}; the communication between thi client and server will be done through object input and output
 * streams, writing either an {@link ObjectRequestPacket} or a {@link MethodRequestPacket} instance on the output stream,
 * stored in {@code outStream} and waiting to read the answer on the input stream, stored in {@code inStream}</p>
 * @see SocketHandler
 * @see SocketServer
 * @see shared.network.SharedProxyServer
 * @see shared.network.SharedProxyClient
 * @see Connection
 */
public class NetworkSocket implements Connection {
    private static final Integer SOCKET_PORT = 1101;
    private static Map<String, Object> exportedObjects = Collections.synchronizedMap(new HashMap<String, Object>());

    private Socket socketProducer;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private String ip;
    private Thread threadConsumer;
    private Integer portConsumer;

    private final String server;
    private final Integer port;

    /**
     * Helper method to set up the <b>server</b> components
     * @param port the listening port for incoming connections
     */
    private void startConsumer(Integer port) {
        try {
            if (threadConsumer == null) {
                this.ip = Connection.getLocalIp("wl");
                // Setup the socket that will listen for incoming connections
                SocketServer socketConsumer = new SocketServer(port, exportedObjects);
                this.portConsumer = socketConsumer.getPort();
                this.threadConsumer = new Thread(socketConsumer);
                this.threadConsumer.start();
            }
        } catch (SocketException es) {
            Logger.log("Unable to resolve local host name/address!");
        }
    }

    /**
     * Helper method to set up the <b>client</b> components
     * @param server the server's address or hostname
     * @param port the server's port for accepting the client's socket
     */
    private void startProducer(String server, Integer port) throws MethodConnectionException {
        try {
            this.ip = Connection.getLocalIp("wl");
            // Setup the socket which will output data to the server
            if (server.equals("")) {
                server = "localhost";
            }
            if (port == 0) {
                socketProducer = new Socket(server, SOCKET_PORT);
            } else {
                socketProducer = new Socket(server, port);
            }
            outStream = new ObjectOutputStream(socketProducer.getOutputStream());
            outStream.flush();
            inStream = new ObjectInputStream(socketProducer.getInputStream());
        } catch (Exception e) {
            throw new MethodConnectionException();
        }
    }

    /**
     * Constructor for the <b>client</b> instance
     * @param server {@code String}
     * @param port {@code Integer}
     * @see NetworkSocket#startProducer(String, Integer)
     */
    public NetworkSocket(String server, Integer port) throws MethodConnectionException {
        this.port = port;
        this.server = server;
        startProducer(this.server, this.port);
    }

    /**
     * Constructor for the <b>client</b> instance
     * @param server {@code String}
     * @see NetworkSocket#startProducer(String, Integer)
     */
    public NetworkSocket(String server) throws MethodConnectionException {
        this.port = SOCKET_PORT;
        this.server = server;
        startProducer(this.server, this.port);
    }

    /**
     * Constructor for the <b>server</b> instance
     * @param port {@code Integer}
     * @see NetworkSocket#startConsumer(Integer)
     */
    public NetworkSocket(Integer port) {
        this.port = port;
        this.server = null;
        startConsumer(this.port);
    }

    /**
     * Constructor for the <b>server</b> instance
     * @see NetworkSocket#startConsumer(Integer)
     */
    public NetworkSocket() {
        this.port = SOCKET_PORT;
        this.server = null;
        startConsumer(this.port);
    }

    @Override
    public String getLocalIp() {
        return this.ip;
    }

    @Override
    public Integer getListeningPort() {
        return this.portConsumer;
    }

    /**
     * @param o {@code Object}
     * @param n {@code String}
     * @see Connection#export(Object, String)
     */
    @Override
    public void export(Object o, String n) {
        startConsumer(this.port);
        try {
            if (o == null) {
                throw new NullPointerException();
            }
            exportedObjects.put(n, o);
        } catch (ClassCastException cce) {
            Logger.log("Error casting given object into Serializable!");
        } catch (NullPointerException npe) {
            Logger.log("Cannot export null object with name " + n);
        }
    }

    /**
     * @param name {@code String}
     * @param <T> destination type
     * @return {@code T}
     * @see Connection#getExported(String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getExported(String name) {
        T exportedObject = null;
        try {
            outStream.writeObject(new ObjectRequestPacket(name));
            exportedObject = (T) inStream.readObject();
        } catch (IOException ioe) {
            Logger.log("Error sending request packet to server!");
        } catch (ClassCastException cce) {
            Logger.log("Error casting server response!");
        } catch (ClassNotFoundException cnfe) {
            Logger.log("Server response object has unknown class!");
        }
        return exportedObject;
    }

    /**
     *
     * @param callee {@code String}
     * @param methodName {@code String}
     * @param argList {@code Object[]}
     * @return {@code Object}
     * @throws MethodConnectionException when there are connection issues with the server or when the desired object or
     * method are not found in the {@code HashMap}
     * @see Connection#invokeMethod(String, String, Object[])
     */
    @Override
    public Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException {
        Object returnValue = null;
        startProducer(this.server, this.port);
        try {
            outStream.writeObject(new MethodRequestPacket(callee, methodName, argList));
            returnValue = inStream.readObject();
        } catch (IOException ioe) {
            Logger.log("Error sending request packet to server!");
        } catch (ClassCastException cce) {
            Logger.log("Error casting server response!");
        } catch (ClassNotFoundException cnfe) {
            Logger.log("Server response object has unknown class!");
        } finally {
            close();
        }
        return returnValue;
    }

    /**
     * Called automatically when an instance is used in a try-with-resources
     * @see Connection#close()
     */
    public void close() {
        try {
            this.inStream.close();
            this.outStream.close();
            this.socketProducer.close();
        } catch (IOException ioe) {
            Logger.log("Error closing streams or socket before terminating consumer!");
        }
    }
}