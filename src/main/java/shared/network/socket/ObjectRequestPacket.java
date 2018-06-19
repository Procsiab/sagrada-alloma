package shared.network.socket;

import java.io.Serializable;

/**
 * <h1>Object Request Packet</h1>
 * <p>When a host wants to know if on another one an object has been exported and bound to the name {@code objectName},
 * it should send and instance of this class to that host: the {@link SocketHandler} instance on the endpoint will answer
 * sending back the requested object, if it is bound to that name</p><br>
 * <p>This class implements {@code Serializable}, so it is suitable to be sent via Socket</p>
 */
final class ObjectRequestPacket implements Serializable {
    public final String objectName;

    /**
     * Send the instance constructed by this method to a host via an {@link java.io.ObjectOutputStream}; the answer will
     * be received on an {@link java.io.ObjectInputStream}
     * @param objectName text bound to an exported object, on the endpoint
     * @see ObjectRequestPacket
     */
    ObjectRequestPacket(String objectName) {
        this.objectName = objectName;
    }
}