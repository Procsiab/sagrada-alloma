package shared.network.socket;

import shared.*;
import shared.network.MethodRouter;
import shared.network.Router;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * <p>This class implements {@code Runnable} to make its instances assignable to a background {@code Thread}; moreover,
 * it implements {@code AutoCloseable} to gracefully terminate without user interaction the network components if needed</p><br>
 * <p>Instances of this class work by obtaining an {@link ObjectInputStream} and an {@link ObjectOutputStream} instance
 * from the socket passed to the constructor; then an object is read from the input stream, and if it was an {@link ObjectRequestPacket}
 * then the return value of {@link SocketHandler#getExported(String)} is written on the output stream; if an instance of
 * {@link MethodRequestPacket} is read from the input stream instead, then the return value of the
 * {@link SocketHandler#invokeMethod(String, String, Object[])} method is written on the output stream. Then all the network
 * resources are released</p>
 * @see NetworkSocket
 * @see SocketServer
 * @see shared.network.Connection
 */
class SocketHandler implements AutoCloseable, Runnable {
    private Socket client;
    private Map<String, Object> exportedObjects;
    private static final Router router = new MethodRouter();

    SocketHandler(Socket client, Map<String, Object> objects) {
        this.client = client;
        this.exportedObjects = objects;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
            Object data = in.readObject();
            Object resp;
            if (data instanceof ObjectRequestPacket) {
                ObjectRequestPacket req = (ObjectRequestPacket) data;
                resp = getExported(req.objectName);
            } else if (data instanceof MethodRequestPacket) {
                MethodRequestPacket req = (MethodRequestPacket) data;
                resp = invokeMethod(req.callee, req.methodName, req.arguments);
            } else {
                throw new ClassNotFoundException();
            }
            out.writeObject(resp);
            out.flush();
        } catch (IOException ioe) {
            // IOException during socket communication could happen multiple times and for different reasons:
            // no message will be logged on the console in this case
        } catch (ClassNotFoundException cnfe) {
            Logger.log("Received data has unknown class!");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                Logger.log("Error closing ObjectInputStream after handling client");
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
                Logger.log("Error closing ObjectOutputStream after handling client");
            }
            this.close();
        }
    }

    @SuppressWarnings("unchecked")
    private  <T> T getExported(String name) {
        T exportedObject = null;
        try {
            exportedObject = (T) exportedObjects.get(name);
        } catch (ClassCastException cce) {
            Logger.log("Error casting exported object " + name + " into destination class!");
        }
        return exportedObject;
    }

    private Object invokeMethod(String callee, String methodName, Object[] argList) {
        try {
            Object e = getExported(callee);
            return router.route(e, methodName, argList);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void close() {
        try {
            client.close();
        } catch (IOException ioe) {
            Logger.log("Error closing socket in SocketHandler");
        }
    }
}