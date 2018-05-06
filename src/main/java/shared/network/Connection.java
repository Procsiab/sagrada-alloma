package shared.network;

public interface Connection extends AutoCloseable {

    void export(Object o, String name);

    String getIp();

    Integer getListeningPort();

    <T> T getExported(String name);

    Object invokeMethod(String callee, String methodName, Object[] argList);

    void close();
}