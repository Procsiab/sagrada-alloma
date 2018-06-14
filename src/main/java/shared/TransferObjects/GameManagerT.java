package shared.TransferObjects;

import shared.Dice;
import shared.RoundTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameManagerT implements Serializable {

    public final ArrayList<PlayerT> vPlayers = new ArrayList<>();
    public final ArrayList<String> publicOCs = new ArrayList<>();
    public final ArrayList<ToolCT> toolCards = new ArrayList<>();
    public final RoundTrack roundTrack;
    public final ArrayList<Dice> pool = new ArrayList<>();
    public final ArrayList<Integer> tCtokens = new ArrayList<>();
    public final Integer pos;

    public GameManagerT(List<PlayerT> vPlayers, List<String> publicOCs,
                        List<ToolCT> toolCards, RoundTrack roundTrack, List<Dice> pool,
                        List<Integer> tCtokens, Integer pos) {

        this.vPlayers.addAll(vPlayers);
        this.publicOCs.addAll(publicOCs);
        this.toolCards.addAll(toolCards);
        this.tCtokens.addAll(tCtokens);
        this.roundTrack = roundTrack;
        this.pool.addAll(pool);
        this.pos = pos;
    }
}