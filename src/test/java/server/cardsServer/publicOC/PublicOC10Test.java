package server.cardsServer.publicOC;

import org.junit.jupiter.api.Test;
import server.Player;
import server.threads.GameManager;
import shared.Dice;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PublicOC10Test {

    @Test
    void use() {
        PublicOC10 oc = new PublicOC10();

        Dice[][] dices = new Dice[4][5];

        //setup overlay
        /*dices[0][0] = new Dice('g', 5);
        dices[0][1] = new Dice('y', 5);
        dices[0][2] = new Dice('y', 5);
        dices[0][3] = new Dice('b', 5);
        dices[0][4] = new Dice('b', 5);
        dices[1][0] = new Dice('g', 5);
        dices[0][2] = new Dice('b', 5);
        dices[0][3] = new Dice('y', 5);
        dices[1][4] = new Dice('y', 5);*/
        dices[1][1] = new Dice('b', 5);
        dices[2][0] = new Dice('b', 5);
        dices[2][2] = new Dice('b', 5);
        dices[2][3] = new Dice('y', 5);
        dices[3][2] = new Dice('y', 5);
        dices[3][3] = new Dice('b', 5);

        Player player = new Player(0, new GameManager(new ArrayList<String>()), "prova");
        player.getOverlay().setDices(dices);

        Integer score = oc.use(player);

        assertEquals(6, score.intValue());
    }
}