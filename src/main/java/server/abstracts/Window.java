package server.abstracts;

import server.Cell;

public abstract class Window {

    public Cell[][] feature;
    public Integer tokens;

    public Cell[][] getFeature() {
        return feature;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setFeature(Cell[][] feature) {
        this.feature = feature;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }
}
