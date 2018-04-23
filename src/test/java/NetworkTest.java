import client.network.NetworkClient;
import server.network.NetworkServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Imports to use mockito and Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class NetworkTest {
    private NetworkClient myNetClient;
    private NetworkServer myNetServer;
    private Foo bar;

    @Before
    public void before() {
        NetworkServer.setInstance();
        NetworkClient.setInstance();
        myNetClient = NetworkClient.getInstance();
        myNetServer = NetworkServer.getInstance();

        bar = mock(Foo.class);
        when(bar.getName()).thenReturn("foobar");
    }

    @Test
    public void exportTest() {
        myNetServer.remotize(bar);
        myNetServer.export(bar, "bar");
        Foo myFoo = (Foo) myNetClient.getExportedObject("bar");
        Assert.assertEquals(bar.getName(), myFoo.getName());
    }
}

/*****************************************************************/
interface SharedFoo extends Remote {
    String getName() throws RemoteException;
}

class Foo implements SharedFoo {
    private String name;
    public Foo(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}