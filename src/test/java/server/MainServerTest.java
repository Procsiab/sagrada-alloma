package server;

import client.MainClient;
import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Test;
import shared.Logger;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainServerTest {
    private void pause(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void main() {

        MainServer.simulation();
        MiddlewareServer middlewareServer = MiddlewareServer.getInstance();
        String player1 = "player1";
        String player2 = "player2";
        String player3 = "player3";
        String player4 = "player4";

        MainClient.simulation(player1);
        MainClient.simulation(player2);
        MainClient.simulation(player3);

        middlewareServer.startGame(player1, "192.168.223.1", -1, false);
        middlewareServer.startGame(player2, "192.168.223.1", -1, false);


        pause(2000);
        System.out.println("uscita player 2");
        middlewareServer.exitGame1(player2);

        pause(2000);
        System.out.println("rientra player 2, entra player3");
        middlewareServer.startGame(player2, "192.168.223.1", -1, false);
        middlewareServer.startGame(player3, "192.168.223.1", -1, false);


        pause(12000);
        System.out.println("scelta finestre");
        middlewareServer.chooseWindowBack(player1, 5);
        middlewareServer.chooseWindowBack(player2, 5);

        pause(25000);

        //offline player2

        //ricollegamento player2
        System.out.println("uscita dal gioco player2");
        middlewareServer.exitGame2(player2);


        pause(9999);
    }
}