package server.threads;

import server.MatchManager;
import shared.Logger;
import shared.logic.GeneralTask;
import shared.logic.Locker;

import java.util.Arrays;


public class TimerNewGame extends GeneralTask {
    public Integer sleepTime;
    NewGameManager newGameManager;
    public final Object obj = new Object();
    private Locker safe = Locker.getSafe();

    TimerNewGame(Integer time, NewGameManager newGameManager){
        sleepTime = time;
        this.newGameManager = newGameManager;
    }

    @Override
    public void run() {
        super.run();

        while (true) {
            synchronized (obj) {
                try {
                    obj.wait(sleepTime);
                } catch (InterruptedException ie){
                    Logger.log("Thread sleep was interrupted!");
                    Logger.strace(ie);
                    Thread.currentThread().interrupt(); //Proper handling of InterruptedException
                }
            }

            synchronized (safe.sLock2){
                if (MatchManager.getInstance().q.size()>1) {
                    newGameManager.start = true;
                    safe.sLock2.notifyAll();
                    break;
                }
            }
        }
    }
}
