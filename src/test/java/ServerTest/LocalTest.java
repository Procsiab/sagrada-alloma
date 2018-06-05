import org.junit.Test;
import server.Player;
import server.threads.GameManager;
import shared.Position;

import java.util.ArrayList;

public class LocalTest {


        @Test
        public static void main(String[] args) {
            ArrayList<String> players = new ArrayList<>();
            players.add("player1");
            players.add("player2");
            players.add("player3");

            GameManager gameManager = new GameManager(players);
            Player player = new Player(gameManager, "player1");
            player.setWindow(0);
            player.placeDice(0, new Position(0, 0));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(1, new Position(0, 1));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(2, new Position(0, 2));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(3, new Position(0, 3));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(4, new Position(0, 4));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(5, new Position(1, 0));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(6, new Position(1, 1));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(7, new Position(1, 2));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(8, new Position(1, 3));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(9, new Position(1, 4));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(10, new Position(2, 0));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(11, new Position(2, 1));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(12, new Position(2, 2));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(13, new Position(2, 3));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(14, new Position(2, 4));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(15, new Position(3, 0));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(16, new Position(3, 1));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(17, new Position(3, 2));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(18, new Position(3, 3));
            player.clearUsedTcAndPlacedDice();
            player.placeDice(19, new Position(3, 4));
            player.clearUsedTcAndPlacedDice();

            //test tc1


        }
    }