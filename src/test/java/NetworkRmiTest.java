import shared.Logger;
import shared.network.Connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Imports to use mockito and Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.network.rmi.NetworkRmi;
import static org.mockito.Mockito.*;

public class NetworkRmiTest {
    private Connection myNetServer;
    private Connection myNetClient;
    private Foo bar;

    @Before
    public void before() {
        myNetServer = new NetworkRmi();
        myNetClient = new NetworkRmi("", 0);

        bar = mock(Foo.class);
        when(bar.getName()).thenReturn("foobar");
    }

    @Test
    public void exportTest() {
        myNetServer.export(bar, "bar");
        SharedFoo myFoo = myNetClient.getExported("bar");
        try {
            Assert.assertEquals(bar.getName(), myFoo.getName());
        } catch (RemoteException e) {
            Logger.log("Error calling remote method!");
        }
        Assert.assertEquals(bar.getName(), myNetClient.invokeMethod("bar", "getName", null));
    }
}

/*****************************************************************/
interface SharedFoo extends Remote {
    String getName() throws RemoteException;
}

class Foo implements SharedFoo {
    private String name;
    Foo(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}