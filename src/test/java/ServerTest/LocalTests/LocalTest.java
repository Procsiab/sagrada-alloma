package ServerTest.LocalTests;

import org.junit.Test;
import server.Player;
import server.threads.GameManager;
import shared.Position;

import java.util.ArrayList;

public class LocalTest {


    @Test
    public static void main(String[] args){
        ArrayList<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");
        players.add("player3");

        GameManager gameManager = new GameManager(players);
        Player player = new Player(gameManager,"player1");

        player.setWindow(0);

        player.placeDice(0, new Position(0,0));


        //test tc1

    }
}
