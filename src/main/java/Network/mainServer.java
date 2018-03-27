package Network;

import Logic.Match;
import Logic.Player;

import java.util.Queue;
import java.util.Vector;

public class mainServer {
    final static Integer maxActivePlayers = 250;

    private Queue<Player> pp2;
    private Queue<Player> pp3;
    private Queue<Player> pp4;
    private Integer IDPlayer = -1;
    private Integer IDMatch = -1;
    private static Vector<Player> p;
    private static Vector<Match> m;
    private static String[][] bindingConf = new String[maxActivePlayers][4];

    public void acceptIncomingConnections(){

    }

    private void Bind(String MAC, String IP, String Port, Player player){
        IDPlayer++;

        this.bindingConf[IDPlayer][0] = MAC;
        this.bindingConf[IDPlayer][1] = IP;
        this.bindingConf[IDPlayer][2] = Port;
        p.add(player);
    }

    public static Vector<Player> getP() {
        return p;
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
        int activePlayers = 0;
        while (activePlayers <= maxActivePlayers) {
            //acceptIncomingConections then create new Player, call Bind, propEnqueueUpd, and activePlayers++
        }

    }
}