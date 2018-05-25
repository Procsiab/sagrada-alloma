package server.abstractsServer;

import server.Player;

import java.io.Serializable;

public abstract class PublicOC implements Serializable {

    public String name;

    public abstract Integer use(Player player);
}
