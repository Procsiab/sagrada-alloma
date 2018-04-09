package ClientP2P.Logic;

public abstract class Card {

    private String Nome;
    private String Descrizione;

    public abstract String getNome();
    public abstract String getDescrizione();
    public abstract boolean use();
}
