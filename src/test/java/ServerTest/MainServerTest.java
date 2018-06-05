package ServerTest;

import server.connection.DummyMiddlewareServer;
import server.threads.MainServer;
import server.threads.GameManager;

import java.util.ArrayList;


import org.junit.jupiter.api.Test;
import shared.Position;
import shared.PositionR;

import static org.junit.Assert.assertEquals;

class MainServerTest {

    public static ArrayList<GameManager> gameManagers = new ArrayList<>();
    public static DummyMiddlewareServer middlewareServer = DummyMiddlewareServer.getInstance();
    public static Object obj;
    public static Integer timeout;

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

    @Test
    void main() {

        MainServer.simulation();
        obj = MainServer.obj;


        pause(1000);

        String player1 = "player1";
        String player2 = "player2";
        String player3 = "player3";
        String player4 = "player4";
        String player5 = "player5";
        String player6 = "player6";
        String player7 = "player7";

        startGame(player1);
        startGame(player2);


        pause(2000);
        System.out.println("uscita player 2");
        middlewareServer.exitGame1(player2);

        pause(2000);
        System.out.println("rientra player 2, entra player3");
        startGame(player2);
        startGame(player3);

        updateGameManagers();


        pause(1000);
        System.out.println("scelta finestre");
        middlewareServer.chooseWindowBack(player1, 5);
        middlewareServer.chooseWindowBack(player2, 5);

        pause(25000);

        //offline player2
        System.out.println("offline player2");
        middlewareServer.setUnresponsive(player2);

        pause(15000);

        //online player2
        System.out.println("online player2");
        middlewareServer.setResponsive(player2);


        startGame(player4);
        startGame(player5);
        startGame(player6);
        startGame(player7);

        updateGameManagers();

        pause(5000);
        //exit player2
        System.out.println("uscita dal gioco player2");
        middlewareServer.exitGame2(player2);

        //vittoria a tavolino
        String tavolo = player5;
        middlewareServer.exitGame2(player7);
        middlewareServer.exitGame2(player6);
        middlewareServer.setUnresponsive(player4);
        middlewareServer.exitGame2(player5);

        pause(1000);
        middlewareServer.setResponsive(player4);
        pause(15000);
        if (gameManagers.get(1) != null) {
            System.out.println("got you");
            tavolo = gameManagers.get(1).getTavolo();
        }

        middlewareServer.placeDice(player3,2,new Position(3,2));
        middlewareServer.useToolC(player3,1,new Position(1,2),new Position(3,4),new Position(1,5),new Position(2,2),new PositionR(2,1),1,2);


        pause(4000);

        //in game player2
        System.out.println("si riconnette player2");
        startGame(player2);


        pause(timeout);



        assertEquals(player4, tavolo);
        pause(8000);
    }
}