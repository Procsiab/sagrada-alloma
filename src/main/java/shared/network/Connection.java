package shared.network;

public interface Connection extends AutoCloseable {

    String SERVER_ADDRESS = "192.168.1.200";

    void export(Object o, String name);

    String getLocalIp();

    Integer getListeningPort();

    <T> T getExported(String name);

    Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException;

    void close();
}