package shared.TransferObjects;

import shared.abstracts.PrivateOC;
import shared.abstracts.Window;
import shared.Overlay;
import shared.Position;

import java.io.Serializable;

public class PlayerT implements Serializable {
    public final String uUID;
    public final PrivateOC privateOC;
    public final Window window;
    public final Overlay overlay;
    public final Integer tokens;
    public final Integer turno;
    public final Integer score;
    public final Integer privateTurn;
    public final Position lastPlaced;

    public PlayerT(String uUID, PrivateOC privateOC,
                   Window window, Overlay overlay,
                   Integer tokens, Integer turno,
                   Integer score, Integer privateTurn,
                   Position lastPlaced) {

        this.uUID = uUID;
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