package shared.network.socket;

import java.io.Serializable;

class MethodRequestPacket implements Serializable {
    public String callee;
    public String methodName;
    public Object[] arguments;

    MethodRequestPacket(String callee, String methodName, Object[] arguments) {
        this.callee = callee;
        this.methodName = methodName;
        this.arguments = arguments;
    }
}