import client.gui.StartGameController;
import client.network.NetworkRmiClient;
import org.junit.After;
import server.MatchManager;
import server.networkS.NetworkRmiServer;
import shared.SharedClientGame;
import shared.SharedServerMatchManager;

//Imports to use mockito and Junit
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(value = Enclosed.class)
public class MatchManagerTest {
    static SharedClientGame myGameController;

    @BeforeClass
    public static void before() {
        NetworkRmiClient.setInstance();
        NetworkRmiServer.setInstance();
        myGameController = mock(StartGameController.class);
    }

    public static class SingletonTests {
        @Before
        public void before() {
            NetworkRmiClient.setInstance();
            NetworkRmiServer.setInstance();
            myGameController = mock(StartGameController.class);
        }

        @Test
        public void getInstanceTest() {
            MatchManager.setInstance();
            assertTrue(MatchManager.getInstance() != null);
            SharedServerMatchManager myMatchManager = MatchManager.getInstance();
            assertThat(MatchManager.getInstance(), is(myMatchManager));
        }
    }

    @RunWith(value = Parameterized.class)
    public static class ConnectionTests {
        @Parameter public int numberPlayers;

        @Parameters(name = "{index}: testing with {0} players")
        public static Object[] testCases1() {
            return new Object[]{100, 249, 250, 300};
        }

        @Before
        public void before() {
            NetworkRmiClient.setInstance();
            NetworkRmiServer.setInstance();
            myGameController = mock(StartGameController.class);
        }

        @Test
        public void startGameTest() {
            final String CONNECTED = "Connection successful. Please wait for other players to connect";
            final String REFUSED = "Too many incoming requests, please try again later. Sorry for that.";
            MatchManager.setInstance();
            if (numberPlayers <= MatchManager.MAX_ACTIVE_PLAYER_REFS) {
                for (int i = 1; i <= numberPlayers; i++) {
                    assertEquals(CONNECTED, MatchManager.getInstance().startGame(myGameController));
                }
            } else {
                for (int i = 1; i <= MatchManager.MAX_ACTIVE_PLAYER_REFS; i++) {
                    assertEquals(CONNECTED, MatchManager.getInstance().startGame(myGameController));
                }
                for (int i = MatchManager.MAX_ACTIVE_PLAYER_REFS + 1; i <= numberPlayers; i++) {
                    assertEquals(REFUSED, MatchManager.getInstance().startGame(myGameController));
                }
            }
        }

        @After
        public void after() {
            int max = 0;
            if (numberPlayers <= MatchManager.MAX_ACTIVE_PLAYER_REFS) {
                max = numberPlayers;
            } else {
                max = MatchManager.MAX_ACTIVE_PLAYER_REFS;
            }
            for (int i = 1; i <= max; i++) {
                assertTrue(MatchManager.getInstance().exitGame1(myGameController));
            }
            assertFalse(MatchManager.getInstance().exitGame1(myGameController));
        }
    }
}