package server.network;

import client.threads.GeneralTask;

import java.rmi.registry.Registry;

public abstract class NetworkServer {
    public static final Integer RMI_PORT = 1099;
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    protected String serverIp;
    protected Registry rmiRegistry;
}