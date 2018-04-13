package server.threads;

import server.logic.ConcurrencyManager;
import server.logic.Locker;
import server.logic.MatchManager;
import shared.SharedClientGame;

import java.util.ArrayList;

public class NewGameManager_2 extends GeneralTask {

    final Locker Safe = Locker.getSafe();

    @Override
    public void run() {
        super.run();

        while (true) {
            synchronized (Safe.Lock2.get(0)) {
                while (MatchManager.getInstance().pp2.size() % 2 != 0) {
                    try {
                        Safe.Lock2.get(0).wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ArrayList<SharedClientGame> clients = new ArrayList<>();
                clients.add(0, MatchManager.getInstance().pp2.remove(0));
                clients.add(1, MatchManager.getInstance().pp2.remove(0));
                ConcurrencyManager.submit(new GameManager(clients));
            }
        }
    }
}