package client.logic;

public abstract class Card {

    private String nome;
    private String descrizione;

    public abstract String getNome();
    public abstract String getDescrizione();
    public abstract boolean use(Integer idPlayer, Integer idMatch);
}
