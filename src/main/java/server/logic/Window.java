package server.logic;

public class Window {

    private final Cell[][] feature = new Cell[4][5];
    private Integer idMatch;

    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public Cell[][] getFeature() {
        return feature;
    }

    public Integer getIdMatch() {
        return idMatch;
    }
}
