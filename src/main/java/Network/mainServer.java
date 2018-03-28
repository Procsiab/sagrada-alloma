package Network;

import Logic.Match;
import Logic.Player;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class mainServer {
    final static Integer maxActivePlayers = 250;

    private Queue<Player> pp2;
    private Queue<Player> pp3;
    private Queue<Player> pp4;
    private AtomicInteger IDPlayer = new AtomicInteger(-1);
    private AtomicInteger IDMatch = new AtomicInteger(-1);
    private static Vector<Player> p = new Vector<Player>();
    private static Vector<Match> m = new Vector<Match>();
    private static String[][] bindingConf = new String[maxActivePlayers][4];




    public void acceptIncomingConnections(){

    }

    private void createAndBind(String MAC, String IP, String Port){

        Player newPlayer = new Player(IDPlayer.get());
        //fill in first empty space
        int k = 0;
        while(k <p.size() && p.get(k)!=null) {
            k++;
        }
        p.add(k,newPlayer);

        IDPlayer.set(k);

        this.bindingConf[IDPlayer.get()][0] = MAC;
        this.bindingConf[IDPlayer.get()][1] = IP;
        this.bindingConf[IDPlayer.get()][2] = Port;
    }

    public static Vector<Player> getP() {
        return p;
    }

    public static Vector<Match> getM() {
        return m;
    }

    public static void setP(Vector<Player> p) {
        mainServer.p = p;
    }

    public static void setM(Vector<Match> m) {
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

    private synchronized boolean tryStartMatch(){

        //check if size of each queue is multiple of its own spec. value and start match in case
    }



    public static void main(String[] args) {
        AtomicInteger activePlayers = new AtomicInteger(0);
        while (true) {
            while (activePlayers == maxActivePlayers) {
                this.wait();
            }
        //acceptIncomingConections then createAndBind, propEnqueueUpd, and activePlayers++
        }
    }
}