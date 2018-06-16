package shared.network;

public interface Connection extends AutoCloseable {

    //String SERVER_ADDRESS = "192.168.1.15";
    String SERVER_ADDRESS = "localhost";

    void export(Object o, String name);

    String getIp();

    Integer getListeningPort();

    <T> T getExported(String name);

    Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException;

    void close();
}