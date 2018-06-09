
import server.Player;
import server.SReferences;
import server.connection.DummyMiddlewareServer;
import server.threads.MainServer;
import server.threads.GameManager;

import java.util.ArrayList;


import org.junit.jupiter.api.Test;
import shared.Cell;
import shared.Dice;
import shared.Position;
import shared.PositionR;

import static org.junit.Assert.assertEquals;

class MainServerTest {

    public static ArrayList<GameManager> gameManagers = new ArrayList<>();
    public static DummyMiddlewareServer middlewareServer = DummyMiddlewareServer.getInstance();
    public static Object obj;
    public static Integer timeout;
    public GameManager gameManager;
    public Player player;


    private void pause(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startGame(String player) {
        middlewareServer.startGame(player, "192.168.223.1", -1, false);
    }

    public void updateGameManagers() {
        Integer i = gameManagers.size();
        while (gameManagers.size() == i)
            try {
                synchronized (obj) {
                    obj.wait();
                    gameManagers = MainServer.getGameManagers();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        timeout = gameManagers.get(0).getTimeout1();
    }

    public void after() {
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

    @org.junit.Test
    public void testCards() {
        ArrayList<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");
        players.add("player3");

        gameManager = new GameManager(players);
        player = new Player(gameManager, "player1");

        player.setGame(gameManager);

        SReferences.addUuidRefEnhanced("player1");
        SReferences.addGameRef(players.get(0), gameManager);
        SReferences.addPlayerRef(players.get(0), player);

        ArrayList<Integer> ais = new ArrayList<>();
        gameManager.setPublicOCs(ais);

        ais.add(0, 0);
        ais.add(1, 1);
        ais.add(2, 2);
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
        //player.placeDice(0, new Position(2, 4));
        //after();
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

        Dice test[][] = new Dice[4][5];
        test[0][0] = new Dice('y', 2);
        test[0][1] = new Dice('b', 3);
        test[0][2] = new Dice('r', 6);
        test[0][3] = new Dice('b', 5);
        test[0][4] = new Dice('v', 1);
        test[1][0] = new Dice('g', 4);
        test[1][1] = new Dice('y', 6);
        test[1][2] = new Dice('g', 5);
        test[1][3] = new Dice('v', 2);
        test[1][4] = new Dice('b', 4);
        test[2][0] = null;
        test[2][1] = new Dice('v', 2);
        test[2][2] = new Dice('r', 6);
        test[2][3] = new Dice('g', 4);
        test[2][4] = null;
        test[3][0] = new Dice('g', 2);
        test[3][1] = new Dice('y', 3);
        test[3][2] = new Dice('v', 2);
        test[3][3] = new Dice('b', 5);
        test[3][4] = new Dice('y', 3);


        player.setTokens(800);
        ArrayList<Dice> pool = gameManager.getPool();

        //test tc1
        player.useTool(players.get(0), 0, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 0, new Position(5, 0), null, null, null, null, 0, +1);
        after();
        player.useTool(players.get(0), 0, new Position(2, 5), null, null, null, null, 0, +1);
        after();
        player.useTool(players.get(0), 0, new Position(2, 0), null, null, null, null, 0, +1);
        after();


        //use tc2
        player.useTool(players.get(0), 1, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 1, new Position(0, 4), new Position(3, 2), null
                , null, null, null, null);
        after();
        player.getOverlay().setDicePosition(null, new Position(3, 3));
        player.useTool(players.get(0), 1, new Position(0, 3), new Position(3, 3), null
                , null, null, null, null);
        after();

        //use tc3
        player.useTool(players.get(0), 2, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 2, new Position(0, 0), new Position(2, -1), null, null, null, null, null);
        player.getOverlay().setDicePosition(null, new Position(2, 0));
        after();
        player.useTool(players.get(0), 2, new Position(0, 2), new Position(2, 0), null, null, null, null, null);
        after();

        ais.set(0, 3);
        ais.set(1, 4);
        ais.set(2, 5);

        //use tc4
        player.useTool(players.get(0), 0, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 0, new Position(1, 1), new Position(0, 2), new Position(1, 4), new Position(0, 3), null, null, null);
        after();

        //use tc5
        player.useTool(players.get(0), 1, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 1, new Position(1, null), null, null, null, new PositionR(4, 0), 0, null);
        after();
        gameManager.getRoundTrack().addDice(new Dice('r', 6), 4);
        player.useTool(players.get(0), 1, new Position(1, 1), null, null, null, new PositionR(4, 0), 0, null);
        after();

        //use tc6
        player.useTool(players.get(0), 2, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 2, new Position(1, 4), null, null, null, null, 2, null);
        after();

        ais.set(0, 6);
        ais.set(1, 7);
        ais.set(2, 8);

        //use tc7
        player.useTool(players.get(0), 0, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 5, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 0, null, null, null, null, null, null, null);
        after();

        //use tc8
        player.useTool(players.get(0), 1, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 1, new Position(4, 4), null, null, null, null, 15, null);
        after();
        player.getOverlay().setDicePosition(null, new Position(1, 4));
        player.useTool(players.get(0), 1, new Position(1, 4), null, null, null, null, -1, null);
        after();
        pool.add(0, new Dice('b', 4));
        player.getOverlay().setDicePosition(null, new Position(1, 4));
        player.useTool(players.get(0), 1, new Position(1, 4), null, null, null, null, 0, null);
        after();

        //use tc9
        player.useTool(players.get(0), 2, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 2, new Position(1, -1), new Position(null, 4), null, null, null, 0, null);
        after();
        player.getOverlay().setDicePosition(null, new Position(1, 4));
        player.getOverlay().setDicePosition(null, new Position(1, 3));
        player.getOverlay().setDicePosition(null, new Position(2, 3));
        player.getOverlay().setDicePosition(null, new Position(3, 3));
        player.getOverlay().setDicePosition(null, new Position(3, 4));
        player.useTool(players.get(0), 2, new Position(1, 0), new Position(2, 4), null, null, null, 0, null);
        after();
        pool.add(0, new Dice('g', 5));
        player.getOverlay().setDicePosition(null, new Position(1, 4));
        player.getOverlay().setDicePosition(null, new Position(1, 3));
        player.getOverlay().setDicePosition(null, new Position(2, 3));
        player.getOverlay().setDicePosition(null, new Position(3, 3));
        player.getOverlay().setDicePosition(null, new Position(3, 4));
        player.useTool(players.get(0), 2, new Position(1, 0), new Position(2, 4), null, null, null, 0, null);
        after();

        ais.set(0, 9);
        ais.set(1, 10);
        ais.set(2, 11);

        //use tc10
        player.useTool(players.get(0), 5, null, null, null, null, null, null, null);
        after();
        player.useTool(players.get(0), 0, new Position(14, 4), null, null, null, null, 0, null);
        after();
        player.getOverlay().setDicePosition(new Dice('g', 3), new Position(2, 4));
        player.useTool(players.get(0), 0, new Position(1, 4), null, null, null, null, 0, null);
        after();
        pool.set(0, new Dice('r', 3));
        player.getOverlay().setDicePosition(new Dice('g', 3), new Position(2, 4));
        player.useTool(players.get(0), 0, new Position(1, 4), null, null, null, null, 0, null);
        after();

        //use tc11
        player.useTool(players.get(0), 1, null, null, null, null, null, null, null);
        after();
        player.getOverlay().setDicePosition(null, new Position(0, 3));
        player.getOverlay().setDicePosition(null, new Position(0, 4));
        player.useTool(players.get(0), 1, new Position(0, 3), null, null, null, null, 0, 4);
        after();
        player.getOverlay().setDicePosition(null, new Position(0, 2));
        player.getOverlay().setDicePosition(null, new Position(0, 3));
        player.getOverlay().setDicePosition(null, new Position(0, 4));
        player.useTool(players.get(0), 1, new Position(0, 3), null, null, null, null, 0, 4);
        after();

        //use tc12
        player.useTool(players.get(0), 2, null, null, null, null, null, null, null);
        after();
        player.getWindow().getMatrices()[3][3] = new Cell('g');
        player.useTool(players.get(0), 2, new Position(2, 0), new Position(0, 2), new Position(2, 2), new Position(3, 3), new PositionR(4, 0), 0, null);
        after();
        player.getOverlay().setDicePosition(new Dice('g', 6), new Position(2, 0));
        player.getOverlay().setDicePosition(new Dice('g', 6), new Position(2, 2));
        player.getOverlay().setDicePosition(new Dice('b', 5), new Position(1, 2));
        player.getWindow().getMatrices()[3][3] = new Cell('g');
        player.useTool(players.get(0), 2, new Position(2, 0), new Position(0, 2), new Position(2, 2), new Position(3, 3), new PositionR(4, 0), 0, null);
        after();


        //use public object
        Integer sum = 0;
        player.setPrivateOC('r');

        ais.clear();

        ais.add(0, 0);
        ais.add(1, 1);
        ais.add(2, 2);
        ais.add(3, 3);
        ais.add(4, 4);
        ais.add(5, 5);
        ais.add(6, 6);
        ais.add(7, 7);
        ais.add(8, 8);
        ais.add(9, 9);
        ais.add(10, 10);

        sum = player.getScore();

        assert (true);

    }

    @Test
    void main() {

        MainServer.simulation();
        obj = MainServer.obj;
        testCards();
    }
}