package ServerTest;

import client.ProxyClient;
import server.Config;
import server.Player;
import server.SReferences;
import server.connection.ProxyServer;
import server.threads.MainServer;
import server.threads.GameManager;

import java.util.ArrayList;
import java.util.HashSet;


import org.junit.jupiter.api.Test;
import shared.*;
import shared.network.SharedProxyClient;

import static org.junit.Assert.*;

class TestMaxNumber {

    public static ArrayList<GameManager> gameManagers = new ArrayList<>();
    public static ProxyServer proxyServer = ProxyServer.getInstance();
    public static Object obj;
    public static Integer timeout;
    public GameManager gameManager;
    public Player player;
    public int maxUsers;


    private void pause(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startGame(String player) {
        SharedProxyClient p = ProxyClient.getInstance();
        proxyServer.startGame(player, player, "192.168.223.1", -1, false, p);
    }

    public void after() {
        while (gameManager.getPool().remove(null)) {
        }
        String s = "";
        for (Dice dice :
                gameManager.getPool().getDices()) {
            s = s + dice.toString() + "; ";
        }
        System.out.println(s + "\n");
        System.out.println(player.getOverlay());
        player.clearUsedTcAndPlacedDice();

    }

    @Test
    void main() {
        MainServer.simulation();
        maxUsers = Config.maxActivePlayerRefs;
        Integer i = 1;
        while (i <= maxUsers + 1) {
            startGame(i.toString());
            i++;
        }

        Integer maxUserTest = maxUsers + 1;
        assertFalse(SReferences.contains(maxUserTest.toString()));
    }
}