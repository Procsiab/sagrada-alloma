package shared.cards.publicOC;

import server.Player;
import shared.Dice;
import shared.Overlay;
import shared.Position;
import shared.abstracts.PublicOC;

public class PublicOC10 extends PublicOC {

    /*
    public Integer use(Player player) {
        Dice[][] dices = player.overlay.getDicePositions().clone();
        return computate(dices, 'b', 0, 0) +
                computate(dices, 'r', 0, 0) +
                computate(dices, 'y', 0, 0) +
                computate(dices, 'v', 0, 0) +
                computate(dices, 'g', 0, 0);
    }
*/

    public Integer use(Player player) {
        Dice[][] dices = player.overlay.getDicePositions().clone();
        return computate(dices, 'b', 0, 0);
    }

    /*
        public Integer computate(Dice[][] dices, Character color, Integer r, Integer c) {

            if (r > 3 || r < 0 || c > 4 || c < 0)
                return 0;
            if (dices[r][c] == null)
                return 0;
            if (dices[r][c].getColor() != color)
                return 0;
            dices[r][c] = null;
            return 1 + computate(dices, color, r - 1, c - 1) +
                    computate(dices, color, r - 1, c + 1) +
                    computate(dices, color, r + 1, c + 1) +
                    computate(dices, color, r + 1, c - 1);
        }
    */
    public Integer computate(Dice[][] dices, Character color, Integer r, Integer c) {

        if (r > 3 || r < 0 || c > 4 || c < 0)
            return 0;
        if (dices[r][c] == null)
            return 0;
        if (dices[r][c].getColor() != color)
            return 1 + computate(dices, dices[r][c].getColor(), r, c);
        dices[r][c] = null;
        return 1 + computate(dices, color, r - 1, c - 1) +
                computate(dices, color, r - 1, c + 1) +
                computate(dices, color, r + 1, c + 1) +
                computate(dices, color, r + 1, c - 1);
    }
}