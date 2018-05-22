package server;

import net.bytebuddy.description.field.FieldDescription;
import server.threads.GameManager;
import shared.Dice;
import shared.Logger;
import shared.Overlay;
import shared.Position;
import shared.abstracts.PrivateOC;
import shared.abstracts.PublicOC;
import shared.abstracts.Window;

import java.util.ArrayList;

public class Player{
    private MatchManager matchManager = MatchManager.getInstance();
    public String uUID;
    public PrivateOC privateOC;
    public Window window;
    public Overlay overlay = new Overlay();
    public Integer tokens;
    public Integer turno = 0;
    public Integer nPlayer;
    public Integer score = 0;
    public Integer privateTurn; //can be either 1 or 2
    public Position lastPlaced;
    public GameManager game;

    public Player(int i, GameManager gameManager, String uUID) {
        this.uUID = uUID;
        this.nPlayer = i;
        this.game = gameManager;
    }

    public Integer getScore() {
        return score;
    }

    public Integer setScore(){
        ArrayList<PublicOC> publicOCS = game.publicOCs;
        for (PublicOC card:
             publicOCS) {
            score = score + card.use(this);
        }

        int i = 0;
        int j = 0;

        while (i<5) {
            while (j < 4) {
                Dice dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.color == privateOC.color)
                        score++;
                j++;
            }
            i++;
        }

            score = score + tokens;
        Logger.log("score of player " +this+ " is " +score.toString());

        return score;
    }

    public void setPrivateOC(Integer n) {
        privateOC = matchManager.privateOCs.get(n);
    }

    public boolean setWindow(Integer n) {
        Logger.log(n.toString());
        if(this.window != null)
            return false;
        this.window = matchManager.windows.get(n);
        return true;
    }

    public boolean placeDice(Dice dice, Position position) {
        return this.window.setDicePosition(this, dice, position);
    }

    public void setTokens() {
        this.tokens = this.window.tokens;
    }
}