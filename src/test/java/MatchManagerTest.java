import client.gui.StartGameController;
import org.junit.Test;
import server.MatchManager;
//import shared.Logger;

//Imports to use mockito and Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.SharedClientGame;
import shared.SharedServerMatchManager;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MatchManagerTest {
    private SharedClientGame myGameController;

    @Before
    public void before() {
        myGameController = mock(StartGameController.class);
    }

    @Test
    public void getInstanceTest() {
        assertTrue(MatchManager.getInstance() == null);
        MatchManager.setInstance();
        SharedServerMatchManager myMatchManager = MatchManager.getInstance();
        assertThat(MatchManager.getInstance(), is(myMatchManager));
    }

    @Test
    public void startGameTest() {
        MatchManager.setInstance();
        for () {

        }
    }
}