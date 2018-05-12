package shared.TransferObjects;

import server.MatchManager;
import server.MiddlewareServer;
import server.Player;
import server.abstracts.PrivateOC;
import server.abstracts.PublicOC;
import server.abstracts.ToolC;
import shared.Dice;
import shared.RoundTrack;

import java.io.Serializable;
import java.util.ArrayList;

public class GameManagerT implements Serializable {

    public final ArrayList<PlayerT> vPlayers;
    public final ArrayList<PrivateOC> privateOCs;
    public final ArrayList<PublicOC> publicOCs;
    public final ArrayList<ToolC> toolCards;
    public final RoundTrack roundTrack;
    public final ArrayList<Dice> pool;

    public GameManagerT(ArrayList<PlayerT> vPlayers, ArrayList<PrivateOC> privateOCs, ArrayList<PublicOC> publicOCs,
                        ArrayList<ToolC> toolCards, RoundTrack roundTrack, ArrayList<Dice> pool) {

        this.vPlayers = vPlayers;
        this.privateOCs = privateOCs;
        this.publicOCs = publicOCs;
        this.toolCards = toolCards;
        this.roundTrack =roundTrack;
        this.pool = pool;
    }
}