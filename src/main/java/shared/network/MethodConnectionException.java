package shared.network;

public class MethodConnectionException extends Exception {
    public MethodConnectionException() {}

    public  MethodConnectionException (String message) {
        super(message);
    }
}