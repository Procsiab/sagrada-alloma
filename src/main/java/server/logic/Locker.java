package server.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Locker {

    private static Locker safe = new Locker();
    public final ReentrantLock lock1 = new ReentrantLock();
    public final ReentrantLock lock2 = new ReentrantLock();
    public final ReentrantLock lock3 = new ReentrantLock();
    public final ReentrantLock lock4 = new ReentrantLock();
    public final ReentrantLock lock5 = new ReentrantLock();
    public final ReentrantLock pR = new ReentrantLock();
    public final ReentrantLock pp1 = new ReentrantLock();
    public final ReentrantLock pp2 = new ReentrantLock();
    public final ReentrantLock pp3 = new ReentrantLock();
    public final ReentrantLock pp4 = new ReentrantLock();
    public final ReentrantLock p = new ReentrantLock();
    public final ReentrantLock m = new ReentrantLock();
    public final ReentrantLock allQPPMA = new ReentrantLock(); //pR, pp1, pp2, pp3, pp4, p, m, activePlayerRefs
    public final ReentrantLock mainServer = new ReentrantLock();
    public final List<ReentrantLock> actionL = new ArrayList<>();//Match.action, Match.loser

    private Locker(){}


    public static Locker getSafe() {
        return safe;
    }

}
