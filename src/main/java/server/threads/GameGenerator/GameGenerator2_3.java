package server.threads.GameGenerator;

import server.MatchManager;
import server.threads.GameManager;
import shared.Logger;
import shared.logic.ConcurrencyManager;
import shared.logic.GeneralTask;
import shared.logic.Locker;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;

@SuppressWarnings("InfiniteLoopStatement")
public class GameGenerator2_3 extends GeneralTask {
    private Integer sleepTime = 15000;
    public final Object obj = MatchManager.obj2;
    public static boolean start = false;

    public synchronized static void setStart(Boolean value) {
        start = value;
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
                    if (queue.size() == 4 || queue.size() < 2 || !start)
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
                if (start)
                    Thread.sleep(sleepTime);
                else if (queue.size() > 1) {
                    Thread.sleep(sleepTime);
                    setStart(true);
                }
            } catch (Exception e) {
                Logger.log("Error waiting on lock!");
                Logger.log(e.toString());
            }
        }
    }
}