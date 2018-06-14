import shared.Logger;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Imports to use mockito and Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shared.network.MethodConnectionException;
import shared.network.rmi.NetworkRmi;
import static org.mockito.Mockito.*;

public class NetworkRmiTest {
    private NetworkRmi myNetClient;
    private NetworkRmi myNetServer;
    private Foo bar;

    @Before
    public void before() {
        myNetServer = new NetworkRmi();
        myNetClient = new NetworkRmi("");

        bar = mock(Foo.class);
        when(bar.getName()).thenReturn("foobar");
    }

    @Test
    public void exportTest() {
        myNetServer.export(bar, "bar");
        SharedFoo myFoo = myNetClient.getExported("bar");
        try {
            Assert.assertEquals(bar.getName(), myFoo.getName());
        } catch (RemoteException re) {
            Logger.log("Error calling remote method getName() through reference myFoo");
            Logger.strace(re);
        }
        try {
            Assert.assertEquals(bar.getName(), myNetClient.invokeMethod("bar", "getName", null));
        } catch (MethodConnectionException mce) {
            Logger.strace(mce);
        }
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