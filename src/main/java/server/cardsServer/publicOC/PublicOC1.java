package server.cardsServer.publicOC;

import server.Player;
import shared.Dice;
import shared.Overlay;
import server.abstractsServer.PublicOC;

import java.util.ArrayList;

public class PublicOC1 extends PublicOC {

    public final String name = "PublicOC1";

    @Override
    public Integer use(Player player) {
        //gain 5 points for each row with no repeated colors
        Overlay overlay = player.overlay;

        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;

        ArrayList<Character> colors= new ArrayList<>();
        colors.add('b');
        colors.add('r');
        colors.add('g');
        colors.add('y');
        colors.add('v');

        while (i<4){
            colors.clear();
            colors.add('b');
            colors.add('r');
            colors.add('g');
            colors.add('y');
            colors.add('v');
            while (j<5){
                dice= overlay.getDicePositions()[i][j];
                if (dice != null)
                    colors.remove(dice.color);
                j++;
            }
            i++;
            if (colors.isEmpty())
                sum = sum + 6;
        }
        return sum;
    }
}
