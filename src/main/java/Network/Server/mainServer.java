package Network.Server;

import Logic.*;
import Network.Network;
import com.sun.javafx.beans.IDProperty;
import sun.awt.image.ImageWatched;

import java.io.*;
import java.rmi.Naming;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class mainServer {
    //create an object of mainServer
    private static final mainServer Instance = new mainServer();
    private static final Integer maxActivePlayer = 250;
    private final Locker Safe = Locker.getSafe();
    private static String[][] bindingConfig = new String[maxActivePlayer][3];
    private LinkedList<PlayerRef> pR = new LinkedList<PlayerRef>();
    private LinkedList<Player> p = new LinkedList<Player>();
    private LinkedList<Match> m = new LinkedList<Match>();
    private LinkedList<PlayerRef> pp1 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp2 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp3 = new LinkedList<PlayerRef>();
    private LinkedList<PlayerRef> pp4 = new LinkedList<PlayerRef>();
    private AtomicInteger activePlayerRef = new AtomicInteger(0);

    //make the constructor private so that this class cannot be instantiated
    private mainServer() {
        //should be empty
        System.out.println("mainServer class instance was born");
    }

    //Get the only object available
    public static mainServer getInstance() {
        return Instance;
    }

    public LinkedList<PlayerRef> getpR(){
        return pR;
    }

    public LinkedList<Player> getP() {
        return p;
    }

    public LinkedList<Match> getM() {
        return m;
    }

    public int createAndBindUpd(String MAC, String IP, String Port, String Name, Integer nMates) {
        //this is called from ListeningChannel on new player request and create a temporary player reference

        //fill in the first empty place

        Integer k = 0;
        PlayerRef playerRef;

        synchronized (Safe.mainServer) {

            while (k < pR.size() && pR.get(k) != null)
                k++;
            playerRef = new PlayerRef(k, Name, nMates); //send IDPlayer to playerRef. He will use that to introduce himself.
            pR.add(k, playerRef);

            activePlayerRef.incrementAndGet();

            bindingConfig[k][0] = MAC;
            bindingConfig[k][1] = IP;
            bindingConfig[k][2] = Port;
            System.out.println("Bound queued player to ID " + k.toString());

            if (nMates == 1) {
                    pp1.add(playerRef);
            } else if (nMates == 2) {
                    pp2.add(playerRef);
            } else if (nMates == 3) {
                    pp3.add(playerRef);
            } else if (nMates == 4) {
                    pp4.add(playerRef);
            }

        }
        tryStartMatch();
        return k;
    }

    public void tryStartMatch() {

        PlayerRef p1, p2, p3, p4;
        int i = 0;
        int count = 0;
        LinkedList<PlayerRef> arr = new LinkedList<>();

        synchronized (Safe.mainServer) {
            while(i<pp1.size()){
                if(pp1.get(i)!= null){
                    //add new player, match bla bla
                }
                i++;
            }
            i = 0;

            while (i<pp2.size()){
                if(pp2.get(i)!= null){
                    count++;
                    arr.add(pp2.get(i));
                }
                if(count == 2){
                    //istanzia giocatori nuovo match invia arr, nuovo thread TurnManager
                }
            }

            while (i<pp2.size()){
                if(pp2.get(i)!= null){
                    count++;
                    arr.add(pp2.get(i));
                }
                if(count == 2){
                    //istanzia giocatori nuovo match invia arr, nuovo thread TurnManager
                }
            }

            while (i<pp2.size()){
                if(pp2.get(i)!= null){
                    count++;
                    arr.add(pp2.get(i));
                }
                if(count == 2){
                    //istanzia giocatori nuovo match invia arr, nuovo thread TurnManager
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        // Create singleton pointer, Instance is already enabled
        mainServer server = mainServer.getInstance();

        try {
            // Create an instance of Network, which will have the role of server's interface
            Network netIface = new Network(server);

            // Format an URL string for that interface, to be used in RMI registry
            String rmiUrl = "//" + netIface.getServerIp() + ":" + Network.RMI_PORT.toString() + "/"
                    + Network.RMI_IFACE_NAME;

            // Bind the interface to that symbolic URL in the RMI registry
            Naming.rebind(rmiUrl, netIface);

        } catch (Exception e) { // Better exception handling
            e.printStackTrace();
        }





        // Spostare e predisporre ListeningChannel per eventuale chat di gruppo;

        ListeningChannel listener = new ListeningChannel(Network.SOCKET_PORT);
        try {
            ConcurrencyManager.submit(listener);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }



        System.out.println("Press any key to teardown");
        ConcurrencyManager.ThreadManager.shutdown();
    }
}