package shared.network;

public interface Connection extends AutoCloseable {

    String SERVER_ADDRESS = "10.14.2.103";

    void export(Object o, String name);

    String getIp();

    Integer getListeningPort();

    <T> T getExported(String name);

    Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException;

    void close();
}