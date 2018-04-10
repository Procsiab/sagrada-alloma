package server.logic;

public abstract class Card {

    private String name;
    private String description;

    public abstract String getName();
    public abstract String getDescription();
    public abstract boolean use(Integer idPlayer, Integer idMatch);
}