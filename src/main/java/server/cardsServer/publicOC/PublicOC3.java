package server.cardsServer.publicOC;

import server.Player;
import shared.Dice;
import shared.Overlay;
import server.abstractsServer.PublicOC;

import java.util.HashSet;
import java.util.Set;

public class PublicOC3 extends PublicOC {
    @Override
    public Integer use(Player player) {
        //gain 5 points for each row with no repeated colors
        Overlay overlay = player.overlay;

        int i = 0;
        int j = 0;
        int sum = 0;
        int esito = 1;

        Dice dice;

        Set<Integer> numbers= new HashSet<>();

        while (i<5){
            numbers.clear();
            while (j<4){
                dice= overlay.getDicePositions()[i][j];
                if (dice != null)
                    if(!numbers.add(dice.value))
                        esito = 0;
                j++;
            }
            i++;
            if (esito == 1)
                sum = sum + 4;
            esito = 1;
        }
        return sum;
    }
}
