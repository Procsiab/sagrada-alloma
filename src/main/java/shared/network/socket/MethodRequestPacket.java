package shared.network.socket;

import java.io.Serializable;

final class MethodRequestPacket implements Serializable {
    public final String callee;
    public final String methodName;
    public final Object[] arguments;

    MethodRequestPacket(String callee, String methodName, Object[] arguments) {
        this.callee = callee;
        this.methodName = methodName;
        this.arguments = arguments;
    }
}