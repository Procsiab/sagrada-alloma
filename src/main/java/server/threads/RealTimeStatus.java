package server.threads;

import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import server.Config;
import server.Player;
import server.SReferences;
import server.concurrency.GeneralTask;
import server.connection.ProxyServer;
import shared.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RealTimeStatus extends GeneralTask {
    private ProxyServer proxyServer = ProxyServer.getInstance();
    private List<String> players = new ArrayList<>();
    private Set<String> online = new HashSet<>();
    private Set<String> offline = new HashSet<>();
    private Integer time;

    public RealTimeStatus(List<String> players) {
        this.players.addAll(players);
        this.time = Config.timeout6;
    }

    /**
     * give each player notfication
     *
     * @param s1 is the connected player
     * @param s2 is the disconected player.
     *           either one of them is null.
     */
    private void spread(String s1, String s2) {
        for (String player :
                players) {
            String rem = SReferences.getNickNameRef(player);
            if (s2 == null && !s1.equals(rem)) {
                proxyServer.onTimeStatus(player, s1, null);
            } else if (s1 == null && !s2.equals(rem))
                proxyServer.onTimeStatus(player, null, s2);
        }
        Logger.log(SReferences.getGameRef(players.get(0)) + " notify players");
    }

    @Override
    public void run() {
        Boolean t = true;
        String nick;

        while (t) {
            for (String player :
                    players) {
                nick = SReferences.getNickNameRef(player);

                if (nick == null)
                    return;

                if (proxyServer.ping(player)) {
                    if (online.add(player)) {
                        offline.remove(player);
                        spread(nick, null);
                    }
                } else if (offline.add(player)) {
                    online.remove(player);
                    spread(null, nick);
                }

                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Logger.log("Interrupted Exception");
                }
                if (Thread.interrupted())
                    return;
            }
        }
    }
}