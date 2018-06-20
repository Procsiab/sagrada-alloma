package server.threads;

import server.Config;
import server.Player;
import server.SReferences;
import server.concurrency.GeneralTask;
import server.connection.ProxyServer;
import shared.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RealTimeStatus extends GeneralTask {
    private ProxyServer proxyServer = ProxyServer.getInstance();
    private List<String> players;
    private Set<String> online = new HashSet<>();
    private Set<String> offline = new HashSet<>();
    private Integer time;

    public RealTimeStatus(List<String> players) {
        this.players = players;
        this.time = Config.timeout5;
    }

    private void spread(String s1, String s2) {
        for (String player :
                players) {
            proxyServer.onTimeStatus(player, s1, s2);
        }
    }

    @Override
    public void run() {
        Boolean t = true;
        String nick;

        while (t) {
            int i = 0;
            String player;
            while (i < players.size()) {
                player = players.get(i);
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
                i++;
            }
        }
    }
}