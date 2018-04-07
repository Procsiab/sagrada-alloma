package Logic.Concurrency;

import Logic.Locker;
import Logic.Match;
import Logic.MatchManager;
import Logic.Player;

public class AutoUpdateVectors extends GeneralTask {

    Locker Safe = Locker.getSafe();

    @Override
    public void run() {
        synchronized (Safe.allQPPMA) {
            super.run();
            for (Match m : MatchManager.getInstance().getM()) {
                if (m.getToDelete() == 1) {
                    for (Player p : m.getPlayers()) {
                        MatchManager.getInstance().getP().remove(p.getIDMatch());
                    }
                }
                MatchManager.getInstance().getM().remove(m);
            }
        }
    }
}