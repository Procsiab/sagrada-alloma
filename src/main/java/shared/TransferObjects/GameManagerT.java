package shared.TransferObjects;

import shared.Dice;
import shared.RoundTrack;

import java.io.Serializable;
import java.util.ArrayList;

public class GameManagerT implements Serializable {

    public final ArrayList<PlayerT> vPlayers;
    public final ArrayList<Character> privateOCs;
    public final ArrayList<String> publicOCs;
    public final ArrayList<ToolCT> toolCards;
    public final RoundTrack roundTrack;
    public final ArrayList<Dice> pool;
    public final Integer pos;

    public GameManagerT(ArrayList<PlayerT> vPlayers, ArrayList<Character> privateOCs, ArrayList<String> publicOCs,
                        ArrayList<ToolCT> toolCards, RoundTrack roundTrack, ArrayList<Dice> pool,
                        ArrayList<Integer> tCtokens, Integer pos) {

        this.vPlayers = vPlayers;
        this.privateOCs = privateOCs;
        this.publicOCs = publicOCs;
        this.toolCards = toolCards;
        this.roundTrack = roundTrack;
        this.pool = pool;
        this.pos = pos;
    }
}