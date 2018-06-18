package shared.network;

/**
 * <h1>Method Connection Exception</h1>
 * <p>This exception is thrown whenever a connection problem occurs during a network method call through the method
 * {@link Connection#invokeMethod(String, String, Object[])}; it is caught into the classes {@link client.ProxyClient}
 * and {@link server.connection.ProxyServer}</p>
 */
public class MethodConnectionException extends Exception {
    public MethodConnectionException() {}

    public  MethodConnectionException (String message) {
        super(message);
    }
}