package server.threads;

import server.MatchManager;
import shared.Logic.ConcurrencyManager;
import shared.Logic.GeneralTask;
import shared.Logic.Locker;
import shared.SharedClientGame;

import java.sql.Time;
import java.util.ArrayList;

public class NewGameManager extends GeneralTask {
    private final Locker Safe = Locker.getSafe();
    private Integer sleepTime = 10000;
    public boolean start = false;

    @Override
    public void run() {
        super.run();

        TimerNewGame timerNewGame = new TimerNewGame(10000, this);
        ConcurrencyManager.submit(timerNewGame);

        while (true) {
            synchronized (Safe.SLock2) {
                while (MatchManager.getInstance().Q.size() != 4 && start == false) {
                    try {
                        Safe.SLock2.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ArrayList<SharedClientGame> clients = new ArrayList<>(MatchManager.getInstance().Q.size());
                int i = 0;
                while (i < MatchManager.getInstance().Q.size()) {
                    clients.add(MatchManager.getInstance().Q.remove(0));
                    i++;
                }
                ConcurrencyManager.submit(new GameManager(clients));
            }
        }
    }
}