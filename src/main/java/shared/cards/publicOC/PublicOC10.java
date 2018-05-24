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
        while (i < 4) {
            j = 0;
            while (j < 5) {
                sum = sum + computate(dices, 'a', i, j, -1);
                j++;
            }
            i++;
        }
        return sum;
    }


    public Integer computate(Dice[][] dices, Character color, Integer r, Integer c, Integer dA) {

        if (r > 3 || r < 0 || c > 4 || c < 0)
            return 0;
        if (dices[r][c] == null)
            return 0;
        if (dices[r][c].getColor() != color)
            return computate(dices, dices[r][c].getColor(), r, c, -1);
        dices[r][c] = null;
        if (dA == 2)
            dA = 1;
        if (dA == 0)
            dA = 2;
        if (dA == -1)
            dA = 0;
        return dA + computate(dices, color, r - 1, c - 1, dA) +
                computate(dices, color, r - 1, c + 1, dA) +
                computate(dices, color, r + 1, c + 1, dA) +
                computate(dices, color, r + 1, c - 1, dA);
    }
}