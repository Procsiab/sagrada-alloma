/*package Network.Server;

import Logic.Frame;
import Logic.Match;
import Logic.Player;
import Logic.Window;
import Network.Network;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

//TODO FROM SCRATCH mainServer: Problems encountered are: -inconsistent storage of IDPLAYER and IDMATCH as class variable
//TODO they can change constantly. Therefore missing reference in subsequent methods. -Missing IDMATCH declared final
//TODO during creation of player. Creation of player therefore should happen after Match has been initialized.
//TODO Finally - mainServer is not a singleton class.
//TODO mainServer is now replaced.


public class mainServer2 {
    private final static Integer maxActivePlayers = 250;
    private Queue<Player> pp2;
    private Queue<Player> pp3;
    private Queue<Player> pp4;
    private static ArrayList<Player> p = new ArrayList<Player>();
    private static ArrayList<Match> m = new ArrayList<Match>();
    private static String[][] bindingConf = new String[maxActivePlayers][4];
    private Integer activePlayers;

    public mainServer() {
        this.pp2 = new LinkedList<Player>();
        this.pp3 = new LinkedList<Player>();
        this.pp4 = new LinkedList<Player>();
        this.activePlayers = new Integer(0);
    }

    public void acceptIncomingConnections() {
        //TODO Open a service socket and wait for clients (should start a thread)
        //TODO Obtain these values from client
        String mac = "DE:AD:BE:EF", ip = "1.1.1.1", port = "666", nmates = "6", name = "Asdrubale";

        this.createAndBind(mac, ip, port, name);


        //TODO Remove this sleep, for debug purpose only
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createAndBind(String MAC, String IP, String Port, String Name){





        Integer IDPlayer;
        Integer IDMatch;

        //fill in first empty space
        int k = 0;
        while(k <p.size() && p.get(k)!=null) {
            k++;
        }

        IDPlayer = k;
        Player newPlayer = new Player(IDPlayer, IDMatch, Name, new Frame(new Window()), 0);
        p.add(k,newPlayer);
        activePlayers++;


        mainServer.bindingConf[IDPlayer][0] = MAC;
        mainServer.bindingConf[IDPlayer][1] = IP;
        mainServer.bindingConf[IDPlayer][2] = Port;





        this.propEnqueueUpd(p.get(IDPlayer), Integer.parseInt(nmates));
        System.out.printf("Il giocatore %s si è connesso (ID: %d)\n", p.get(IDPlayer).getName(), IDPlayer);
    }

    public static ArrayList<Player> getP() {
        return p;
    }

    public static ArrayList<Match> getM() {
        return m;
    }

    public static void setP(ArrayList<Player> p) {
        mainServer.p = p;
    }

    public static void setM(ArrayList<Match> m) {
        mainServer.m = m;
    }

    public void propEnqueueUpd(Player player, Integer nMates){
        if(nMates == 1){
            //start new solo game
            return ;
        }
        if(nMates == 2) {
            synchronized (pp2) {
                pp2.add(player);
            }
        }
        if(nMates == 3) {
            synchronized (pp3) {
                pp3.add(player);
            }
        }
        if(nMates == 4) {
            synchronized (pp4) {
                pp4.add(player);
            }
        }
        tryStartMatch();
    }

    private boolean tryStartMatch(){
        //check if size of each queue is multiple of its own spec. value and start match in case
        return false;
    }

    @SuppressWarnings("InfiniteLoopStatement") // Stop up the fu*** warnings about non-ending loop
    private void start() throws InterruptedException {
        while (true) {
            while (this.activePlayers == maxActivePlayers) {
                this.wait();
            }
            //acceptIncomingConections then createAndBind, propEnqueueUpd
            this.acceptIncomingConnections();
        }
    }

    public static void main(String[] args) {


        mainServer server = new mainServer();
        try {
            try {
                // Create an instance of Network, which will have the role of server's interface

                Network netIface = new Network();
                // Format an URL string for that interface, to be used in RMI registry
                String rmiUrl = "//" + netIface.getServerIp() + ":" + Network.RMI_PORT.toString() + "/"
                        + Network.RMI_IFACE_NAME;
                // Bind the interface to that symbolic URL in the RMI registry
                Naming.rebind(rmiUrl, netIface);

            } catch (Exception e) { //TODO: Better exception handling
                e.printStackTrace();
            }
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

*/