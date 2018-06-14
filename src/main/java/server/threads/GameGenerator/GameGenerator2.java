package server.threads.GameGenerator;

import server.MatchManager;
import server.threads.GameManager;
import shared.Logger;
import server.concurrency.ConcurrencyManager;
import server.concurrency.GeneralTask;

import java.util.ArrayList;
import java.util.Queue;

@SuppressWarnings("InfiniteLoopStatement")
public class GameGenerator2 extends GeneralTask {
    private final Object obj = MatchManager.getObj();

    private static synchronized void setStart() {
        GameGenerator1.setStart(false);
    }

    @Override
    public void run() {
        super.run();
        Logger.log("GameGenerator2 online");
        ArrayList<String> clients;
        Queue<String> queue = MatchManager.getQ();
        boolean t = true;
        while (t) {
            try {
                synchronized (obj) {
                    if (queue.size() != 4)
                        obj.wait();
                    else {
                        clients = new ArrayList<>(queue.size());
                        clients.addAll(queue);
                        queue.clear();
                        setStart();

                        ConcurrencyManager.submit(new GameManager(clients));
                    }
                }
            } catch (Exception e) {
                Logger.log("Error waiting on lock!");
                Logger.log(e.toString());
            }
        }
    }
}