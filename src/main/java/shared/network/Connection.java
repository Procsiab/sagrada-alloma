package shared.network;

import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <h1>Connection Interface</h1>
 * <p>This interface can be used to abstract the {@link shared.network.rmi.NetworkRmi} and {@link shared.network.socket.NetworkSocket}
 * classes, and use them in a transparent way; they both implement the following methods, however you <i><b>should</b></i> look at the
 * class' specific documentation to learn the differences on how to call these methods</p>
 * @see AutoCloseable
 * @see NetworkSocket
 * @see NetworkRmi
 */
public interface Connection extends AutoCloseable {

    /**
     * Register on the connection's server an {@code Object}, binding it to a name; then it will be callable through its reference obtained by {@link #getExported(String)}
     * @param o object to be exported on the connection's server
     * @param name text string bound to the exported object; you will access the exported object through its exported name
     * @see shared.network.socket.NetworkSocket#export(Object, String)
     * @see shared.network.rmi.NetworkRmi#export(Object, String)
     */
    void export(Object o, String name);

    /**
     * Obtain the IPv4 address of the machine on which is being run the instance of {@code Connection} you call this method on
     * @return IP address version 4, of the machine which is running the JVM with this instance in it
     */
    String getLocalIp();

    /**
     * Obtain the port on which the {@code Connection} instance will accept or send
     * @return port number used by the {@code Connection} instance to exchange data
     * @see NetworkSocket#getListeningPort()
     * @see NetworkRmi#getListeningPort()
     */
    Integer getListeningPort();

    /**
     * Obtain a reference to a registered object, which should have been prior exported using {@link Connection#export(Object, String)};
     * this is a generic method which will convert the returned reference into the destination type, for example:<br>
     * {@code DestClass dc = getExported("Foo")}<br>
     * will try to obtain an exported reference, bound to the name {@code "Foo"}, and then will try to cast it into
     * the destination type {@code DestClass}
     * @param name text representing the name the object you are looking for was bound to, what {@link Connection#export(Object, String)}
     *             was called on the object you want the reference to
     * @param <T> destination type for the obtained reference - which will be an {@code Object} by default; a cast will be performed before returning
     * @return reference to the exported object, cast to the destination type
     */
    <T> T getExported(String name);

    /**
     * Perform a method call through a network segment, from a {@code Connection} instance to another one on a different
     * JVM or on the same one; you can call this method only on objects registered prior on the server through the
     * {@link Connection#export(Object, String)} method. You must keep a reference to the name used at export-time, to
     * provide it as this method's first parameter
     * @param callee name of the exported object you want to call the method on (the text bound to it at export-time)
     * @param methodName name of the method you want to call, as a {@code String} object
     * @param argList a list of method's arguments; method signatures are position-wise, so you <b><i>must</i></b> place
     *                the parameter in the correct order
     * @return return value of the invoked method, obtained as an {@code Object}
     * @throws MethodConnectionException when connection issues occur between client and server; also an error messag
     * will be printed to tell more about the problem
     */
    Object invokeMethod(String callee, String methodName, Object[] argList) throws MethodConnectionException;

    /**
     * This method is used to tear down the networking setup of a {@code Connection} instance; also, it is called
     * automatically in a <i>try-with-resources</i> block, thanks to this interface extending {@code AutoCloseable}
     */
    void close();

    /**
     * Obtain local machine address in IPv4 format, by enumerating network interfaces; if no network interface is
     * detected, loopback address is used by default
     * @param iface string or substring to look for when enumerating interfaces: the interface which name contains
     * {@code iface} will have its IPs enumerated
     * @return string representing the first IPv4 address of the found interface; if no interface is found which name
     * matches {@code iface}, then {@code "localhost"} is returned
     * @throws SocketException when there are issues in the network interfaces enumeration; should be caught from outside
     */
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