package server.threads;

import server.MatchManager;
import shared.Logger;
import shared.SharedServerGameManager;
import shared.logic.ConcurrencyManager;
import shared.logic.GeneralTask;
import shared.logic.Locker;
import shared.SharedClientGame;

import java.util.ArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class NewGameManager extends GeneralTask {
    private final Locker safe = Locker.getSafe();
    private Integer sleepTime = 1000000;
    public boolean start = false;

    @Override
    public void run() {
        super.run();

        //start below when two clients connects, and handle client deletion
        TimerNewGame timerNewGame = new TimerNewGame(100000, this);
        ConcurrencyManager.submit(timerNewGame);

        while (true) {
            synchronized (safe.sLock2) {
                //safe.sLock2.lock();
                while (MatchManager.getInstance().q.size() != 4 && start == false) {
                    try {
                        safe.sLock2.wait();
                    } catch (Exception e) {
                        Logger.log("Error waiting on lock!");
                        Logger.log(e.toString());
                    }
                }
                ArrayList<SharedClientGame> clients = new ArrayList<>(MatchManager.getInstance().q.size());
                int i = 0;
                //TODO: check if size is correct
                while (i < MatchManager.getInstance().q.size()) {
                    clients.add(MatchManager.getInstance().q.remove(0));
                    i++;
                }
                ConcurrencyManager.submit(new GameManager(clients, clients.size()));
                //safe.sLock2.unlock();
            }
        }
    }
}