package ClientP2P.Logic;

public class Window {

    private final Cell[][] feature = new Cell[4][5];
    private Integer IDMatch;

    public void setIDMatch(Integer IDMatch) {
        this.IDMatch = IDMatch;
    }

    public Cell[][] getFeature() {
        return feature;
    }

    public Integer getIDMatch() {
        return IDMatch;
    }
}
