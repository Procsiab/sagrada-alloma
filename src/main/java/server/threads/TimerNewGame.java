package server.threads;

import server.MatchManager;
import shared.Logic.GeneralTask;
import shared.Logic.Locker;


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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (safe.SLock2){
                if (MatchManager.getInstance().Q.size()>1) {
                    newGameManager.start = true;
                    notifyAll();
                    break;
                }
            }
        }
    }
}
