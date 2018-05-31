package server.cardsServer.publicOC;

import server.Player;
import server.abstractsServer.PublicOC;
import shared.Dice;
import shared.Overlay;

import java.util.HashSet;
import java.util.Set;

public class PublicOC6 extends PublicOC {

    public Integer use(Player player) {
        Overlay overlay = player.getOverlay();

        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        Set<Integer> shades1 = new HashSet<>();
        Set<Integer> shades2 = new HashSet<>();
        Set<Integer> shades3 = new HashSet<>();
        Set<Integer> shades4 = new HashSet<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (!shades1.add(dice.value))
                        if (!shades2.add(dice.value))
                            if (!shades3.add(dice.value))
                                shades4.add(dice.value);
                j++;
            }
            j = 0;
            i++;
        }

        if (shades1.size() == 6)
            sum = sum + 5;
        if (shades2.size() == 6)
            sum = sum + 5;
        if (shades3.size() == 6)
            sum = sum + 5;
        if (shades4.size() == 6)
            sum = sum + 5;

        return sum;
    }
}
