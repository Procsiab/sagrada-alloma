import shared.Logger;
import shared.network.Connection;
import shared.network.MethodConnectionException;
import shared.network.socket.NetworkSocket;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

//Imports to use mockito and Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NetworkSocketTest {
    private Connection myNetServer;
    private Connection myNetClient;
    private SharedBar foo;

    @Before
    public void before() {
        foo = new Bar("foobar");
    }

    @Test
    public void getExportTest() throws InterruptedException {
        myNetServer = new NetworkSocket();
        TimeUnit.MILLISECONDS.sleep(1000);
        Logger.log("Exporting Foo from server");
        myNetServer.export(foo, "foo");
        TimeUnit.MILLISECONDS.sleep(100);

        try {
            myNetClient = new NetworkSocket("");
        } catch (MethodConnectionException mce) {
            Logger.strace(mce);
        }
        Logger.log("Retrieving Foo from server");
        SharedBar myBar = myNetClient.getExported("foo");
        Assert.assertEquals(foo.getName(), myBar.getName());
        try {
            Assert.assertEquals(foo.getName(), myNetClient.invokeMethod("foo", "getName", null));
        } catch (MethodConnectionException mce) {
            Logger.strace(mce);
        }
    }
}

/*****************************************************************/
interface SharedBar extends Serializable {
    String getName();
}

class Bar implements SharedBar {
    private String name;
    Bar(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}