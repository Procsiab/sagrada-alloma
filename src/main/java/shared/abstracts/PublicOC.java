package shared.abstracts;

import server.Player;

import java.io.Serializable;

public abstract class PublicOC implements Serializable {

    public abstract Integer use(Player player);
}
