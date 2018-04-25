import client.gui.StartGameController;
import client.network.NetworkClient;
import server.MatchManager;

//Imports to use mockito and Junit
import org.junit.Before;
import server.network.NetworkServer;
import shared.SharedClientGame;
import shared.SharedServerMatchManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(value = Parameterized.class)
public class MatchManagerTest {

    private SharedClientGame myGameController;
    @Parameter public int numberPlayers;

    @Parameters(name = "{index}: testing with {0} players")
    public static Object[] data() {
        return new Object[]{10, 100, 255, 300};
    }

    @Before
    public void before() {
        NetworkClient.setInstance();
        NetworkServer.setInstance();
        myGameController = mock(StartGameController.class);
    }

    @Test
    public void getInstanceTest() {
        assertTrue(MatchManager.getInstance() == null);
        MatchManager.setInstance();
        assertTrue(MatchManager.getInstance() != null);
        SharedServerMatchManager myMatchManager = MatchManager.getInstance();
        assertThat(MatchManager.getInstance(), is(myMatchManager));
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
}