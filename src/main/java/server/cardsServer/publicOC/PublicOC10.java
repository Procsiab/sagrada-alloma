package server.cardsServer.publicOC;

import server.Player;
import shared.Dice;
import server.abstractsServer.PublicOC;
import shared.Overlay;

import java.util.ArrayList;

public class PublicOC10 extends PublicOC {

    public Integer use(Player player) {
        Overlay overlay = player.getOverlay().deepClone();
        Dice[][] dices = overlay.getDicePositions();
        int i = 1;
        int j = 1;
        int sum = 0;
        while (i < 5) {
            j = 1;
            while (j < 6) {
                sum = sum + computate(dices, 'a', i, j, true);
                j++;
            }
            i++;
        }
        return sum;
    }

    private Integer computate(Dice[][] dices, Character color, Integer r, Integer c, Boolean firstTime) {

        if (r > 4 || r < 1 || c > 5 || c < 1)
            return 0;
        if (dices[r - 1][c - 1] == null)
            return 0;
        if (dices[r - 1][c - 1].getColor() != color)
            return computate(dices, dices[r - 1][c - 1].getColor(), r, c, true);
        dices[r - 1][c - 1] = null;
        Integer ul = computate(dices, color, r - 1, c - 1, false);
        Integer ur = computate(dices, color, r - 1, c + 1, false);
        Integer dr = computate(dices, color, r + 1, c + 1, false);
        Integer dl = computate(dices, color, r + 1, c - 1, false);
        if (firstTime)
            return ul + ur + dr + dl;
        return 1 + ul + ur + dr + dl;
    }
}