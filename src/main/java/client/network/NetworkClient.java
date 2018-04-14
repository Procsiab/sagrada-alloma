package client.network;

import client.threads.GeneralTask;

import java.rmi.registry.Registry;

public abstract class NetworkClient {
    public static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "NetworkServer";
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    protected String clientIp;
    protected Registry rmiRegistry;
}