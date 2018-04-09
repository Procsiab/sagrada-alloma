package ClientP2P.Logic.Cards;

import ClientP2P.Logic.Card;
import ClientP2P.Logic.MatchManager;

public class ToolCard1 extends Card {

    private String Nome = "1";
    private String Descrizione;

    @Override
    public String getNome() {
        return Nome;
    }

    @Override
    public String getDescrizione() {
        return null;
    }

    @Override
    public boolean use(Integer IDPlayer, Integer IDMatch) {
        MatchManager.getInstance().getM();
    }
}
