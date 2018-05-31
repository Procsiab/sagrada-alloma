package server.cardsServer.publicOC;

import server.Player;
import server.abstractsServer.PublicOC;
import shared.Dice;
import shared.Overlay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PublicOC7 extends PublicOC {
    public Integer use(Player player) {
        Overlay overlay = player.getOverlay();

        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        ArrayList<Boolean> shades5 = new ArrayList<>();
        ArrayList<Boolean> shades6 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.value == 5)
                        shades5.add(true);
                    else if (dice.value == 6)
                        shades6.add(true);
                j++;
            }
            j = 0;
            i++;
        }

        shades5.trimToSize();
        shades6.trimToSize();

        return Math.min(shades5.size(), shades6.size()) * 2;
    }
}