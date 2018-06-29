package shared.TransferObjects;

import server.SReferences;
import shared.Dice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameManagerT implements Serializable {

    public final ArrayList<PlayerT> vPlayers = new ArrayList<>();
    public final ArrayList<String> publicOCs = new ArrayList<>();
    public final ArrayList<ToolCT> toolCards = new ArrayList<>();
    public final ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();
    public final ArrayList<Dice> pool = new ArrayList<>();
    public final ArrayList<Integer> tCtokens = new ArrayList<>();
    public final ArrayList<String> online = new ArrayList<>();
    public final ArrayList<String> offline = new ArrayList<>();
    public final Integer pos;

    public GameManagerT(List<PlayerT> vPlayers, List<String> publicOCs,
                        List<ToolCT> toolCards, List<ArrayList<Dice>> dices, List<Dice> pool,
                        List<Integer> tCtokens, Set<String> active, List<String> everybody, Integer pos) {

        this.vPlayers.addAll(vPlayers);
        this.publicOCs.addAll(publicOCs);
        this.toolCards.addAll(toolCards);
        this.tCtokens.addAll(tCtokens);
        this.roundTrack.addAll(dices);
        this.pool.addAll(pool);
        this.pos = pos;

        for (String player :
                everybody) {
            String nickName = SReferences.getNickNameRef(player);
            if (active.contains(player))
                online.add(nickName);
            else
                offline.add(nickName);
        }
    }
}