package server.threads;

import server.logic.Locker;
import server.logic.Match;
import server.logic.MatchManager;
import server.logic.Player;

public class AutoUpdateVectors extends GeneralTask {

    Locker safe = Locker.getSafe();

    @Override
    public void run() {
        synchronized (safe.allQPPMA) {
            super.run();
            for (Match m : MatchManager.getInstance().getM()) {
                if (m.getToDelete() == 1) {
                    for (Player p : m.getPlayers()) {
                        MatchManager.getInstance().getP().remove(p.getIdMatch());
                    }
                }
                MatchManager.getInstance().getM().remove(m);
            }
        }
    }
}