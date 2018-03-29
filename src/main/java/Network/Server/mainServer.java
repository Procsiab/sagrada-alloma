package Network.Server;

import Logic.Match;
import Logic.Player;
import Logic.PlayerRef;
import Network.Network;

import java.io.*;
import java.rmi.Naming;
import java.util.LinkedList;
import java.util.Queue;

public class mainServer {


    //create an object of mainServer
    private static final mainServer Instance = new mainServer();
    private static final Integer maxActivePlayer = 250;
    private static String[][] bindingConfig = new String[maxActivePlayer][3];
    private LinkedList<PlayerRef> pR = new LinkedList<PlayerRef>();
    private LinkedList<Player> p = new LinkedList<Player>();
    private LinkedList<Match> m = new LinkedList<Match>();
    private Queue<PlayerRef> pp1 = new LinkedList<PlayerRef>();
    private Queue<PlayerRef> pp2 = new LinkedList<PlayerRef>();
    private Queue<PlayerRef> pp3 = new LinkedList<PlayerRef>();
    private Queue<PlayerRef> pp4 = new LinkedList<PlayerRef>();
    private Integer ActivePLayer = 0;


    //make the constructor private so that this class cannot be instantiated
    private mainServer() {
        //should be empty
        System.out.println("Greetings from Server :)");
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

    public void createAndBindUpd(String MAC, String IP, String Port, String Name, Integer nMates) {
        //this is called from ListeningChannel on new player request and create a temporary player reference

        //fill in the first empty place
        Integer k = 0;
        while (k < p.size() && p.get(k) != null)
            k++;
        PlayerRef playerRef = new PlayerRef(k, Name, nMates); //send IDPlayer to player. He will use that to introduce himself.
        pR.add(k,playerRef);

        bindingConfig[k][0] = MAC;
        bindingConfig[k][1] = IP;
        bindingConfig[k][2] = Port;

        if (nMates == 1) {
            pp1.add(playerRef);
        } else if (nMates == 2) {
            pp2.add(playerRef);
        } else if (nMates == 3) {
            pp3.add(playerRef);
        } else if (nMates == 4) {
            pp4.add(playerRef);
        }

        tryStartMatch();
    }

    public void cancelAndUnbind(Integer IDPlayer){
        //remove playerRef from pR and from queue

    }

    public void tryStartMatch(){
        //check if there is a chance to play
    }

        public static void main(String args[]) {

            mainServer Server = mainServer.getInstance();

            //start ListeningChannel
            int port = Integer.parseInt(args[0]);
            try {
                Thread t = new ListeningChannel(port);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                // Create an instance of Network, which will have the role of server's interface

                Network netIface = new Network();
                // Format an URL string for that interface, to be used in RMI registry
                String rmiUrl = "//" + netIface.getServerIp() + ":" + Network.RMI_PORT.toString() + "/"
                        + Network.RMI_IFACE_NAME;
                // Bind the interface to that symbolic URL in the RMI registry
                Naming.rebind(rmiUrl, netIface);

            } catch (Exception e) { //TODO Better exception handling
                e.printStackTrace();
            }
        }


}