package server.cardsServer.publicOC;

import server.Player;
import server.abstractsServer.PublicOC;
import shared.Dice;
import shared.Overlay;

import java.util.ArrayList;

public class PublicOC9 extends PublicOC {
    public Integer use(Player player) {
        Overlay overlay = player.getOverlay();

        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        ArrayList<Boolean> shades1 = new ArrayList<>();
        ArrayList<Boolean> shades2 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.value == 1)
                        shades1.add(true);
                    else if (dice.value == 2)
                        shades2.add(true);
                j++;
            }
            j = 0;
            i++;
        }

        shades1.trimToSize();
        shades2.trimToSize();

        return Math.min(shades1.size(), shades2.size()) * 2;
    }
}
