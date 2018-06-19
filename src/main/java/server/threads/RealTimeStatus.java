package server.threads;

import server.SReferences;
import server.concurrency.GeneralTask;
import server.connection.ProxyServer;
import shared.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RealTimeStatus extends GeneralTask {
    ProxyServer proxyServer = ProxyServer.getInstance();
    List<String> players;
    Set<String> online = new HashSet<>();
    Set<String> offline = new HashSet<>();

    public RealTimeStatus(List<String> players) {
        this.players = players;
    }

    private void spread(String s1, String s2) {
        for (String player :
                online) {
            proxyServer.onTimeStatus(player, s1, s2);
        }
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
                    Thread.sleep(2000);
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