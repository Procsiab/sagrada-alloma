package server.threads;

import server.MatchManager;
import shared.Logger;
import shared.logic.ConcurrencyManager;
import shared.logic.GeneralTask;
import shared.logic.Locker;

import java.util.ArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class NewGameManager extends GeneralTask {
    private final Locker safe = Locker.getSafe();
    private Integer sleepTime = 10000;
    public final Object obj = new Object();
    public boolean start = false;
    public boolean timer = false;

    @Override
    public void run() {
        super.run();

        //ConcurrencyManager.submit(timerNewGame);

        ArrayList<String> clients;

        boolean t = true;
        TimerNewGame timerNewGame = new TimerNewGame(sleepTime, this, obj);

        while (t) {
            synchronized (safe.sLock2) {
                while (MatchManager.q.size() != 4) {
                    try {
                        safe.sLock2.wait();
                        if (MatchManager.q.size() > 1) {
                            if (!timer) {
                                timerNewGame.openDeadEnd();
                                ConcurrencyManager.submit(timerNewGame);
                                timer=true;
                            }
                            if(start) {
                                timerNewGame.setDeadEnd();
                                synchronized (obj){
                                    obj.notifyAll();
                                }
                                timer = false;
                                start = false;
                                break;
                            }
                        }

                    } catch (Exception e) {
                        Logger.log("Error waiting on lock!");
                        Logger.log(e.toString());
                    }
                }
                clients = new ArrayList<>(MatchManager.q.size());
                clients.addAll(MatchManager.q);
                MatchManager.q.clear();
            }
            Logger.log("GmaeManager submit");
            ConcurrencyManager.submit(new GameManager(clients));
        }
    }
}