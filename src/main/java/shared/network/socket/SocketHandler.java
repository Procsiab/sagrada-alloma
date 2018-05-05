package shared.network.socket;

import shared.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

class SocketHandler implements Runnable, Closeable {
    private Socket client;
    private Map<String, Object> exportedObjects;

    SocketHandler(Socket client, Map<String, Object> objects) {
        this.client = client;
        this.exportedObjects = objects;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {

            System.out.println("weeeeeee SocketHandler");


            in = new ObjectInputStream(client.getInputStream());
            out = new ObjectOutputStream(client.getOutputStream());
            Object data = in.readObject();
            Object resp;
            Logger.log("Reading client data from socket...");
            //TODO Use smarter packet handling
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
            Logger.log("Error in handling client input!");
            Logger.strace(ioe);
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
            Class[] parameters;
            Object o = getExported(callee);
            Method m;
            if (o != null) {
                if (argList == null) {
                    parameters = null;
                    m = o.getClass().getDeclaredMethod(methodName, parameters);
                } else {
                    parameters = Arrays.stream(argList).map(Object::getClass).toArray(Class[]::new);
                    m = o.getClass().getDeclaredMethod(methodName, parameters);
                }
                m.setAccessible(true);
                return m.invoke(o, argList);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException npe) {
            Logger.log("Could not find requested object " + callee + " among exported ones!");
        } catch (ClassCastException cce) {
            Logger.log("The given object should extend Serializable!");
        } catch (NoSuchMethodException nsme) {
            Logger.log("Requested method " + methodName + " was not found in " + callee + " class!");
            Logger.strace(nsme);
        } catch (InvocationTargetException ite) {
            Logger.log("An exception occurred in method " + methodName + "!");
            Logger.strace(ite);
        } catch (IllegalAccessException iae) {
            Logger.log("Error accessing method " + methodName + " on object " + callee + "!");
            Logger.strace(iae);
        }
        return null;
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