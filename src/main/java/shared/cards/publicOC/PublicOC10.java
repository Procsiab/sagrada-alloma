package shared.cards.publicOC;

import server.Player;
import shared.Dice;
import shared.Overlay;
import shared.Position;
import shared.abstracts.PublicOC;

public class PublicOC10 extends PublicOC {

    public Integer use(Player player) {
        Dice[][] dices = player.overlay.getDicePositions().clone();
        int i = 0;
        int j = 0;
        int sum = 0;
        while (i < 3) {
            j = 0;
            while (j < 4) {
                sum = sum + computate(dices, 'a', i, j, false);
                j++;
            }
            i++;
        }
        return sum;
    }


    public Integer computate(Dice[][] dices, Character color, Integer r, Integer c, Boolean dA) {

        if (r > 3 || r < 0 || c > 4 || c < 0)
            return 0;
        if (dices[r][c] == null)
            return 0;
        if (dices[r][c].getColor() != color)
            return computate(dices, dices[r][c].getColor(), r, c, false);
        if (dA) {
            dices[r][c] = null;
            return 1 + computate(dices, color, r - 1, c - 1, true) +
                    computate(dices, color, r - 1, c + 1, true) +
                    computate(dices, color, r + 1, c + 1, true) +
                    computate(dices, color, r + 1, c - 1, true);
        }
        dices[r][c] = null;
        return computate(dices, color, r - 1, c - 1, true) +
                computate(dices, color, r - 1, c + 1, true) +
                computate(dices, color, r + 1, c + 1, true) +
                computate(dices, color, r + 1, c - 1, true);
    }

}