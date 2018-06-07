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
    private final Object obj2 = MatchManager.getObj2();

    private static synchronized void setStart(Boolean value) {
        GameGenerator1.setStart(value);
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
                synchronized (obj2) {
                    if (queue.size() != 4)
                        obj2.wait();
                    else {
                        clients = new ArrayList<>(queue.size());
                        clients.addAll(queue);
                        queue.clear();
                        setStart(false);

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