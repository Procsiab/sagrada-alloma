import org.junit.Test;
import server.Player;
import server.SReferences;
import server.threads.GameManager;
import shared.Dice;
import shared.Position;
import shared.PositionR;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class LocalTest {

    public static GameManager gameManager;
    public static Player player;

    public static void after() {
        while (gameManager.getPool().remove(null)) {
        }
        String s = "";
        for (Dice dice :
                gameManager.getPool()) {
            s = s + dice.toString() + "; ";
        }
        System.out.println(s + "\n");
        System.out.println(player.getOverlay());
        player.clearUsedTcAndPlacedDice();

    }

    @Test
    public static void main(String[] args) {
        ArrayList<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");
        players.add("player3");

        LocalTest.gameManager = new GameManager(players);
        LocalTest.player = new Player(gameManager, "player1");
        player.setGame(gameManager);

        SReferences.addUuidRefEnhanced("player1");
        SReferences.addGameRef(players.get(0), gameManager);
        SReferences.addPlayerRef(players.get(0), player);

        ArrayList<Integer> ais = new ArrayList<>();
        ais.add(200);
        ais.add(200);
        ais.add(200);
        gameManager.settCtokens(ais);

        ais.set(0, 0);
        ais.set(1, 1);
        ais.set(2, 2);
        gameManager.setToolCards(ais);

        player.setWindow(0);
        player.placeDice(0, new Position(0, 0));
        after();
        player.placeDice(0, new Position(0, 1));
        after();
        player.placeDice(0, new Position(0, 2));
        after();
        player.placeDice(0, new Position(0, 3));
        after();
        player.placeDice(0, new Position(0, 4));
        after();
        player.placeDice(0, new Position(1, 0));
        after();
        player.placeDice(0, new Position(1, 1));
        after();
        player.placeDice(0, new Position(1, 2));
        after();
        player.placeDice(0, new Position(1, 3));
        after();
        player.placeDice(0, new Position(1, 4));
        after();
        //player.placeDice(10, new Position(2, 0));
        //after();
        player.placeDice(0, new Position(2, 1));
        after();
        player.placeDice(0, new Position(2, 2));
        after();
        player.placeDice(0, new Position(2, 3));
        after();
        player.placeDice(0, new Position(2, 4));
        after();
        player.placeDice(0, new Position(3, 0));
        after();
        player.placeDice(0, new Position(3, 1));
        after();
        player.placeDice(0, new Position(3, 2));
        after();
        player.placeDice(0, new Position(3, 3));
        after();
        player.placeDice(0, new Position(3, 4));
        after();


        player.setTokens(800);
        ArrayList<Dice> pool = gameManager.getPool();

        //test tc1
        player.useTool(players.get(0), 0, new Position(2, 0), null, null, null, null, 0, +1);

        after();

        //use tc2
        player.useTool(players.get(0), 1, new Position(0, 3), new Position(3, 3), null
                , null, null, null, null);
        after();

        //use tc3
        player.getOverlay().setDicePosition(null, new Position(2, 0));
        player.useTool(players.get(0), 2, new Position(0, 2), new Position(2, 0), null, null, null, null, null);
        after();

        ais.set(0, 3);
        ais.set(1, 4);
        ais.set(2, 5);
        gameManager.setToolCards(ais);

        //use tc4
        player.useTool(players.get(0), 0, new Position(1, 1), new Position(0, 2), new Position(1, 4), new Position(0, 3), null, null, null);
        after();

        //use tc5
        gameManager.getRoundTrack().addDice(new Dice('r', 6), 4);
        player.useTool(players.get(0), 1, new Position(1, 1), null, null, null, new PositionR(4, 0), 0, null);
        after();

        //use tc6
        player.useTool(players.get(0), 2, new Position(1, 4), null, null, null, null, 2, null);
        after();

        ais.set(0, 6);
        ais.set(1, 7);
        ais.set(2, 8);
        gameManager.setToolCards(ais);

        //use tc7
        player.useTool(players.get(0), 0, null, null, null, null, null, null, null);
        after();

        //use tc8
        player.useTool(players.get(0), 0, new Position(1,4), null, null, null, null, 0, null);
        after();


        assertEquals(true,true);
    }
}