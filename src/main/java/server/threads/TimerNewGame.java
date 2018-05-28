package server.threads;

import server.MatchManager;
import server.SReferences;
import shared.Logger;
import shared.logic.GeneralTask;
import shared.logic.Locker;

import java.util.Arrays;


public class TimerNewGame extends GeneralTask {
    public Integer sleepTime;
    public NewGameManager newGameManager;
    public boolean deadEnd;
    public final Object obj;
    private Locker safe = Locker.getSafe();

    TimerNewGame(Integer time, NewGameManager newGameManager, Object obj) {
        sleepTime = time;
        this.newGameManager = newGameManager;
        this.obj = obj;
    }

    public void setDeadEnd(){
        this.deadEnd = true;
    }

    public void openDeadEnd(){
        this.deadEnd = false;
    }

    @Override
    public void run() {
        Logger.log("timer started");
        super.run();

        synchronized (obj) {
            try {
                obj.wait(sleepTime);
                if(deadEnd)
                    return;
            } catch (InterruptedException ie) {
                Logger.log("Thread sleep was interrupted!");
                Logger.strace(ie);
                Thread.currentThread().interrupt(); //Proper handling of InterruptedException
            }
        }

        synchronized (safe.sLock2) {
            newGameManager.timer = false;
            newGameManager.start = true;
            safe.sLock2.notifyAll();
        }
    }
}

