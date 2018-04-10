package client.logic.cards;

import client.logic.Card;
import client.logic.MatchManager;

public class ToolCard1 extends Card {

    private String nome = "1";
    private String descrizione;

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescrizione() {
        return null;
    }

    @Override
    public boolean use(Integer idPlayer, Integer idMatch) {
        MatchManager.getInstance().getM();
    }
}
