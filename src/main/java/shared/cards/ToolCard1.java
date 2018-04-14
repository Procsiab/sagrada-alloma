package shared.cards;

import shared.Card;

public class ToolCard1 extends Card {

    private String name = "1";
    private String description = null;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean use(Integer idPlayer, Integer idMatch) {
        return true; //Should implement true method
    }
}
