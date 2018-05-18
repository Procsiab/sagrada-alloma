package shared.cards.publicOC;

import server.Player;
import shared.Dice;
import shared.Overlay;
import shared.abstracts.PublicOC;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PublicOC2 extends PublicOC {
    @Override
    public Integer use(Player player) {
        //gain 5 points for each row with no repeated colors
        Overlay overlay = player.overlay;

        int i = 0;
        int j = 0;
        int sum = 0;
        int esito = 1;

        Dice dice;

        Set<Character> colors= new HashSet<>();

        //switch column in getDicePositions

        while (i<5){
            colors.clear();
            while (j<4){
                dice= overlay.getDicePositions()[i][j];
                if (dice != null)
                    if(!colors.add(dice.color))
                        esito = 0;
                j++;
            }
            i++;
            if (esito == 1)
                sum = sum + 5;
            esito = 1;
        }
        return sum;
    }
}
