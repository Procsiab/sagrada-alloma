package shared.network.socket;

import java.io.Serializable;

/**
 * <p>When a host wants to call a method of an object on another host, it should send and instance of this class to that
 * host: the {@link SocketHandler} instance on the endpoint will answer sending back the return value of that method, if
 * the object name, the method name and the arguments are correct.</p><br>
 * <p>This class implements {@code Serializable}, so it is suitable to be sent via Socket</p>
 */
final class MethodRequestPacket implements Serializable {
    public final String callee;
    public final String methodName;
    public final Object[] arguments;

    /**
     * Send the instance constructed by this method to a host via an {@link java.io.ObjectOutputStream}; the answer will
     * be received on an {@link java.io.ObjectInputStream}
     * @param callee text bound to an exported object, on the endpoint
     * @param methodName text representing the method name, the same as in the signature
     * @param arguments array containing the method's parameters, in the same order as in the signature
     */
    MethodRequestPacket(String callee, String methodName, Object[] arguments) {
        this.callee = callee;
        this.methodName = methodName;
        this.arguments = arguments;
    }
}