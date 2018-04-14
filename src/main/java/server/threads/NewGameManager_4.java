package server.threads;

import server.MatchManager;
import shared.Logic.ConcurrencyManager;
import shared.Logic.GeneralTask;
import shared.Logic.Locker;
import shared.SharedClientGame;

import java.util.ArrayList;

public class NewGameManager_4 extends GeneralTask {
    final Locker Safe = Locker.getSafe();

    @Override
    public void run() {
        super.run();

        while (true) {
            synchronized (Safe.SLock2.get(2)) {
                while (MatchManager.getInstance().pp2.size() % 4 != 0) {
                    try {
                        Safe.SLock2.get(2).wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ArrayList<SharedClientGame> clients = new ArrayList<>(4);
                clients.add(0, MatchManager.getInstance().pp2.remove(0));
                clients.add(1, MatchManager.getInstance().pp2.remove(0));
                clients.add(2, MatchManager.getInstance().pp2.remove(0));
                clients.add(3, MatchManager.getInstance().pp2.remove(0));
                ConcurrencyManager.submit(new GameManager(clients));
            }
        }
    }
}