package server.cardsServer.publicOC;

import server.Player;
import server.abstractsServer.PublicOC;
import shared.Dice;
import shared.Overlay;

import java.util.HashSet;
import java.util.Set;

public class PublicOC5 extends PublicOC {

    public Integer use(Player player) {
        Overlay overlay = player.getOverlay();

        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        Set<Character> colors1 = new HashSet<>();
        Set<Character> colors2 = new HashSet<>();
        Set<Character> colors3 = new HashSet<>();
        Set<Character> colors4 = new HashSet<>();
        Set<Character> colors5 = new HashSet<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (!colors1.add(dice.color))
                        if (!colors2.add(dice.color))
                            if (!colors3.add(dice.color))
                                if (!colors4.add(dice.color))
                                    colors5.add(dice.color);
                j++;
            }
            j = 0;
            i++;
        }

        if (colors1.size() == 5)
            sum = sum + 4;
        if (colors2.size() == 5)
            sum = sum + 4;
        if (colors3.size() == 5)
            sum = sum + 4;
        if (colors4.size() == 5)
            sum = sum + 4;
        if (colors5.size() == 5)
            sum = sum + 4;

        return sum;
    }
}