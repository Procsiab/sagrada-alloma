package server.logic;

public abstract class Card {

    public String name;
    public String description;

    public abstract String getName();
    public abstract String getDescription();
    public abstract boolean use(Integer idPlayer, Integer idMatch);
}