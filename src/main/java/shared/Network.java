package shared;

/**
 * This class can be used to share global configuration among its specializations
 */
public abstract class Network {
    public static final Integer SOCKET_PORT = 1101;
    public static final Integer RMI_PORT = 1099;
    public static final Integer RMI_IFACE_PORT = 1100;
}