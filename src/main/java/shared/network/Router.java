package shared.network;

import java.rmi.RemoteException;

/**
 * <p>This interface will route method calls performed on some object, based on the method name as string and on parameters
 * as an array. Routing is also based on the object's nature, among {@code SharedProxyClient} or {@code SharedProxyServer}</p>
 * @see SharedProxyClient
 * @see SharedProxyServer
 */
public interface Router {
    /**
     * This method will route a method call between a {@link SharedProxyServer} and a {@link SharedProxyClient} instance,
     * in both directions; also, this method is used in both RMI (always called on the client) and Socket (called on the
     * endpoint) network communication modes.
     * @param callee object on which the method will be called; it <b><i>must</i></b> be either an instance of {@code SharedProxyClient} or
     *          {@code SharedProxyServer}
     * @param methodName text representing the method name: it <b><i>must</i></b> be the same as the method name declared
     *                   in the corresponding interface
     * @param argList list of arguments used, together with the {@code methodName}, to build the method's complete signature;
     *                thus, it is very important to provide the arguments in the correct order
     * @return if the called method has a return value, it is also passed as return value of the {@code route} method
     * in case of a {@code void} method call, {@code null} is returned
     * @throws RemoteException if {@link shared.network.rmi.NetworkRmi#remotize(Object, Integer)} has been called, passing
     * {@code e} as first parameter, this exception could occur, since an RMI call will take place
     */
    Object route(Object callee, String methodName, Object[] argList) throws RemoteException;
}