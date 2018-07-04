package server.threads;

import server.Config;
import server.MatchManager;
import server.threads.GameManager;
import server.threads.Timer;
import shared.Logger;
import server.concurrency.ConcurrencyManager;
import server.concurrency.GeneralTask;

import java.util.ArrayList;
import java.util.Queue;

public class GameGenerator extends GeneralTask {
    private Integer sleepTime = Config.getConfig().timeout1;
    private final Object obj = MatchManager.getObj();
    private ArrayList<String> clients;
    private Queue<String> queue = MatchManager.getQ();

    private void letsTheGameBegin() {
        clients = new ArrayList<>(queue.size());
        clients.addAll(queue);
        queue.clear();
        ConcurrencyManager.submit(new GameManager(clients));
    }

    /**
     * handle the connections request under timing scope.
     * Timer starts when queue has two player and generates the game (if possible) when timer runs out.
     */
    @Override
    public void run() {
        super.run();
        Logger.log("GameGenerator online. Timer runs out every: " + sleepTime / 1000 + "s");

        Timer timer = null;
        boolean t = true;
        while (t) {
            try {
                synchronized (obj) {
                    switch (queue.size()) {
                        case (0):
                        case (1):
                            timer = null;
                            obj.wait();
                            break;
                        case (2):
                            if (timer == null) {
                                timer = new Timer(sleepTime, obj);
                                ConcurrencyManager.submit(timer);
                            } else {
                                if (timer.getExpired()) {
                                    timer = null;
                                    letsTheGameBegin();
                                } else
                                    obj.wait();
                            }
                            break;
                        case (3):
                            if (timer != null && timer.getExpired()) {
                                timer = null;
                                letsTheGameBegin();
                            } else
                                obj.wait();
                            break;
                        case (4):
                            timer = null;
                            letsTheGameBegin();
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                Logger.log("Error waiting on lock!");
                Logger.strace(e);
            }
        }
    }
}