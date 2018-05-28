package server.threads;

import server.MatchManager;
import shared.Logger;
import shared.logic.ConcurrencyManager;
import shared.logic.GeneralTask;

import java.util.ArrayList;
import java.util.Queue;

@SuppressWarnings("InfiniteLoopStatement")
public class GameGenerator4 extends GeneralTask {
    public final Object obj = MatchManager.obj2;
    public synchronized static void setStart(Boolean value) {
        GameGenerator2_3.setStart(value);
    }

    @Override
    public void run() {
        super.run();

        ArrayList<String> clients;
        Queue<String> queue = MatchManager.q;
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
                        setStart(false);

                        Logger.log("GmaeManager submit");
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