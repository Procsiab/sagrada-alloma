package shared.network.socket;

import java.io.Serializable;

class ObjectRequestPacket implements Serializable {
    String objectName;

    ObjectRequestPacket(String objectName) {
        this.objectName = objectName;
    }
}