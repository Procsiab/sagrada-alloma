package shared.network;

public interface Connection {

    void export(Object o, String name);

    String getIp();

    <T> T getExported(String name);

    Object invokeMethod(String callee, String methodName, Object[] argList);
}