package shared.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public interface Connection extends AutoCloseable {

    void export(Object o, String name);

    String getLocalIp();

    Integer getListeningPort();

    <T> T getExported(String name);

    Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException;

    void close();

    static String getLocalIp(String iface) throws SocketException {
        final String IPV4_REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";

        Enumeration<NetworkInterface> netIfaces = NetworkInterface.getNetworkInterfaces();
        while (netIfaces.hasMoreElements()) {

            NetworkInterface element = netIfaces.nextElement();
            if (element.getName().contains(iface)) {

                Enumeration<InetAddress> addresses = element.getInetAddresses();
                while (addresses.hasMoreElements()) {

                    String addr = addresses.nextElement().getHostAddress();
                    if (addr.matches(IPV4_REGEX)) {
                        return addr;
                    }
                }
            }
        }
        return "localhost";
    }
}