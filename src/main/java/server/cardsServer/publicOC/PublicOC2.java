package server.cardsServer.publicOC;

import server.Player;
import shared.Dice;
import shared.Overlay;
import server.abstractsServer.PublicOC;

import java.util.HashSet;
import java.util.Set;

public class PublicOC2 extends PublicOC {
    @Override
    public Integer use(Player player) {
        Overlay overlay = player.getOverlay();

        int i = 0;
        int j = 0;
        int sum = 0;
        int esito = 1;

        Dice dice;

        Set<Character> colors = new HashSet<>();

        while (i < 5) {
            colors.clear();
            while (j < 4) {
                dice = overlay.getDicePositions()[j][i];
                if (dice == null)
                    esito = 0;
                else if (!colors.add(dice.color))
                    esito = 0;
                j++;
            }
            j = 0;
            i++;
            if (esito == 1)
                sum = sum + 5;
            esito = 1;
        }
        return sum;
    }
}