package shared.TransferObjects;

import shared.abstractsShared.PrivateOC;
import shared.abstractsShared.Window;
import shared.Overlay;
import shared.Position;

import java.io.Serializable;

public class PlayerT implements Serializable {
    public final PrivateOC privateOC;
    public final Window window;
    public final Overlay overlay;
    public final Integer tokens;
    public final Integer turno;
    public final Integer score;
    public final Integer privateTurn;
    public final Position lastPlaced;

    public PlayerT(PrivateOC privateOC,
                   Window window, Overlay overlay,
                   Integer tokens, Integer turno,
                   Integer score, Integer privateTurn,
                   Position lastPlaced) {

        this.privateOC = privateOC;
        this.window = window;
        this.overlay = overlay;
        this.tokens = tokens;
        this.turno = turno;
        this.score = score;
        this.privateTurn = privateTurn;
        this.lastPlaced = lastPlaced;
    }
}