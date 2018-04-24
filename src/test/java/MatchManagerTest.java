import client.gui.StartGameController;
import client.network.NetworkClient;
import org.junit.Test;
import server.MatchManager;
//import shared.Logger;

//Imports to use mockito and Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.network.NetworkServer;
import shared.Network;
import shared.SharedClientGame;
import shared.SharedServerMatchManager;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MatchManagerTest {
    private SharedClientGame myGameController;

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
        for (int i = 0; i < MatchManager.MAX_ACTIVE_PLAYER_REFS; i++) {
            assertEquals(CONNECTED, MatchManager.getInstance().startGame(myGameController));
        }
        assertEquals(REFUSED, MatchManager.getInstance().startGame(myGameController));
    }
}