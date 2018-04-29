package shared.network.socket;

import shared.Logger;
import shared.network.Connection;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class NetworkSocket implements Connection, Closeable {
    private static final Integer SOCKET_PORT = 1101;
    private static final String SERVER_ADDRESS = "localhost";
    private static Map<String, Object> exportedObjects = Collections.synchronizedMap(new HashMap<String, Object>());

    private Socket socketProducer;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private String IP;
    private Thread consumer;

    private final String server;
    private final Integer port;

    private void startConsumer(Integer port) {
        try {
            this.IP = InetAddress.getLocalHost().getHostAddress();
            this.consumer = new Thread(new SocketServer(port, exportedObjects));
            this.consumer.start();
        } catch (UnknownHostException uhe) {
            Logger.log("Unable to resolve local host name/address!");
            Logger.strace(uhe);
        }
    }

    private void startProducer(String server, Integer port) {
        try {
            // Setup the socket which will output data to the server
            if (server.equals("")) {
                socketProducer = new Socket(SERVER_ADDRESS, port);
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
    }

    public NetworkSocket(String server) {
        this.port = SOCKET_PORT;
        this.server = server;
    }

    public NetworkSocket(Integer port) {
        this.port = port;
        this.server = null;
        startConsumer(port);
    }

    public NetworkSocket() {
        this.port = SOCKET_PORT;
        this.server = null;
        startConsumer(SOCKET_PORT);
    }

    @Override
    public String getIp() {
        return this.IP;
    }

    @Override
    public void export(Object o, String n) {
        try {
            Serializable s = (Serializable) o;
            exportedObjects.put(n, s);
        } catch (ClassCastException cce) {
            Logger.log("Error casting given object into Serializable!");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getExported(String name) {
        T exportedObject = null;
        startProducer(this.server, this.port);
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
            this.consumer.interrupt();
            this.inStream.close();
            this.outStream.close();
            this.socketProducer.close();
        } catch (IOException ioe) {
            Logger.log("Error closing streams or socket before shutting down!");
            Logger.strace(ioe);
        }
    }
}