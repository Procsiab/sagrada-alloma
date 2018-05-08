package shared.network.socket;

import java.io.Serializable;

final class ObjectRequestPacket implements Serializable {
    public final String objectName;

    ObjectRequestPacket(String objectName) {
        this.objectName = objectName;
    }
}