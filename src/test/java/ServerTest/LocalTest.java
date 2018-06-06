import org.junit.Test;
import server.Player;
import server.SReferences;
import server.threads.GameManager;
import shared.Dice;
import shared.Position;
import shared.PositionR;

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
player.setGame(gameManager);

            SReferences.addUuidRefEnhanced("player1");
            SReferences.addGameRef(players.get(0), gameManager);
            SReferences.addPlayerRef(players.get(0),player);

            ArrayList<Integer> ais = new ArrayList<>();
            ais.add(200);
            ais.add(200);
            ais.add(200);
            gameManager.settCtokens(ais);

            ais.set(0,0);
            ais.set(1,1);
            ais.set(2,2);
            gameManager.setToolCards(ais);

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
            //player.placeDice(10, new Position(2, 0));
            //player.clearUsedTcAndPlacedDice();
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

            //clear pool
            int i = 0;

            player.setTokens(800);

            while (i < gameManager.getPool().size()) {
                Dice dice = gameManager.getPool().get(i);
                if (dice == null) {
                    gameManager.getPool().remove(i);
                } else i++;
            }



            for (Dice d :
                    gameManager.getPool()) {
                System.out.println(d);
            }

            ArrayList<Dice> pool = gameManager.getPool();
            String s = "";

            //test tc1
            player.useTool(players.get(0),0, new Position(2,0),new Position(2,0),null,null,null,0,+1);

            //while (gameManager.getPool().remove(null)){}
            pool.stream()
                    .forEach(System.out::println);
            player.clearUsedTcAndPlacedDice();

            //use tc2
            player.useTool(players.get(0),1, new Position(0,3), new Position(3,3),null
                    ,null,null,null,null);
            //while (gameManager.getPool().remove(null)){}
            for (Dice dice:
                 pool) {
                s= s+dice.toString();
            }
            System.out.println(s+"\n");
            System.out.println(player.getOverlay());
            player.clearUsedTcAndPlacedDice();

            //use tc3
            player.getOverlay().setDicePosition(null,new Position(2,0));
            player.useTool(players.get(0),2, new Position(0,2), new Position(2,0),null,null,null,null,null);
            //while (gameManager.getPool().remove(null)){}
            s = "";
            for (Dice dice:
                    pool) {
                s= s+dice.toString();
            }
            System.out.println(s+"\n");
            System.out.println(player.getOverlay());
            player.clearUsedTcAndPlacedDice();

            ais.set(0,3);
            ais.set(1,4);
            ais.set(2,5);
            gameManager.setToolCards(ais);

            //use tc4
            player.getOverlay().setDicePosition(null,new Position(2,0));
            player.useTool(players.get(0),0, new Position(1,1), new Position(3,4),new Position(2,1),new Position(2,4),null,null,null);
            //while (gameManager.getPool().remove(null)){}
            s = "";
            for (Dice dice:
                    pool) {
                s= s+"; "+dice.toString();
            }
            System.out.println(s+"\n");
            System.out.println(player.getOverlay());
            player.clearUsedTcAndPlacedDice();

            //use tc5
            gameManager.getRoundTrack().addDice(new Dice('v',3),4);
            player.useTool(players.get(0),1, new Position(2,0),null,null,null,new PositionR(4,0),0,null);
            //while (gameManager.getPool().remove(null)){}
            s = "";
            for (Dice dice:
                    pool) {
                s= s+ dice.toString()+"; ";
            }
            System.out.println(s+"\n");
            System.out.println(player.getOverlay());
            player.clearUsedTcAndPlacedDice();

            //use tc6
            player.useTool(players.get(0),2, new Position(1,1), null,null,null,null,2,null);
            //while (gameManager.getPool().remove(null)){}
            s = "";
            for (Dice dice:
                    pool) {
                s= s+ dice.toString()+"; ";
            }
            System.out.println(s+"\n");
            System.out.println(player.getOverlay());
            player.clearUsedTcAndPlacedDice();

        }
    }