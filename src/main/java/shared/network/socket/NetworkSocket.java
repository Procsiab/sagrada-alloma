package shared.network.socket;

import shared.Logger;
import shared.network.Connection;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class NetworkSocket implements Connection {
    private static final Integer SOCKET_PORT = 1101;
    private static final String SERVER_ADDRESS = "localhost";
    private static Map<String, Object> exportedObjects = Collections.synchronizedMap(new HashMap<String, Object>());

    private Socket socketProducer;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private String ip;
    private Thread threadConsumer;
    private Integer portConsumer;

    private final String server;
    private final Integer port;

    private void startConsumer(Integer port) {
        try {
            if (threadConsumer == null) {
                this.ip = InetAddress.getLocalHost().getHostAddress();
                // Setup the socket that will listen for incoming connections
                SocketServer socketConsumer = new SocketServer(port, exportedObjects);
                this.portConsumer = socketConsumer.getPort();
                this.threadConsumer = new Thread(socketConsumer);
                this.threadConsumer.start();
            }
        } catch (UnknownHostException uhe) {
            Logger.log("Unable to resolve local host name/address!");
            Logger.strace(uhe);
        }
    }

    private void startProducer(String server, Integer port) {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
            // Setup the socket which will output data to the server
            if (server.equals("")) {
                server = SERVER_ADDRESS;
            }
            if (port == 0) {
                socketProducer = new Socket(server, SOCKET_PORT);
            } else {
                socketProducer = new Socket(server, port);
            }
            outStream = new ObjectOutputStream(socketProducer.getOutputStream());
            outStream.flush();
            inStream = new ObjectInputStream(socketProducer.getInputStream());
        } catch (IOException ioe) {
            Logger.log("Error while opening socket on server port " + port.toString() + "!");
            Logger.strace(ioe);
        }
    }

    public NetworkSocket(String server, Integer port) {
        this.port = port;
        this.server = server;
        startProducer(this.server, this.port);
    }

    public NetworkSocket(String server) {
        this.port = SOCKET_PORT;
        this.server = server;
        startProducer(this.server, this.port);
    }

    public NetworkSocket(Integer port) {
        this.port = port;
        this.server = null;
        startConsumer(this.port);
    }

    public NetworkSocket() {
        this.port = SOCKET_PORT;
        this.server = null;
        startConsumer(this.port);
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public Integer getListeningPort() {
        return this.portConsumer;
    }

    @Override
    public void export(Object o, String n) {
        startConsumer(this.port);
        try {
            exportedObjects.put(n, o);
        } catch (ClassCastException cce) {
            Logger.log("Error casting given object into Serializable!");
            Logger.strace(cce);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getExported(String name) {
        T exportedObject = null;
        try {
            outStream.writeObject(new ObjectRequestPacket(name));
            exportedObject = (T) inStream.readObject();
        } catch (IOException ioe) {
            Logger.log("Error sending request packet to server!");
            Logger.strace(ioe);
        } catch (ClassCastException cce) {
            Logger.log("Error casting server response!");
        } catch (ClassNotFoundException cnfe) {
            Logger.log("Server response object has unknown class!");
        }
        return exportedObject;
    }

    @Override
    public Object invokeMethod(String callee, String methodName, Object[] argList) {
        Object returnValue = null;
        startProducer(this.server, this.port);
        try {
            outStream.writeObject(new MethodRequestPacket(callee, methodName, argList));
            returnValue = inStream.readObject();
        } catch (IOException ioe) {
            Logger.log("Error sending request packet to server!");
            Logger.strace(ioe);
        } catch (ClassCastException cce) {
            Logger.log("Error casting server response!");
        } catch (ClassNotFoundException cnfe) {
            Logger.log("Server response object has unknown class!");
        }
        return returnValue;
    }

    public void close() {
        try {
            this.inStream.close();
            this.outStream.close();
            this.socketProducer.close();
        } catch (IOException ioe) {
            Logger.log("Error closing streams or socket before shutting down!");
            Logger.strace(ioe);
        }
    }
}