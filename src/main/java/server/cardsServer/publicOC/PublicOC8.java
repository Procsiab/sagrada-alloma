package server.cardsServer.publicOC;

import server.Player;
import server.abstractsServer.PublicOC;
import shared.Dice;
import shared.Overlay;

import java.util.ArrayList;

public class PublicOC8 extends PublicOC {
    public Integer use(Player player) {
        Overlay overlay = player.getOverlay();

        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        ArrayList<Boolean> shades3 = new ArrayList<>();
        ArrayList<Boolean> shades4 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.value == 3)
                        shades3.add(true);
                    else if (dice.value == 4)
                        shades4.add(true);
                j++;
            }
            j = 0;
            i++;
        }

        shades3.trimToSize();
        shades4.trimToSize();

        return Math.min(shades3.size(), shades4.size()) * 2;
    }
}
