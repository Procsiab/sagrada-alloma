package Logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locker {

    private static Locker Safe = new Locker();
    public final ReentrantLock Lock1 = new ReentrantLock();
    public final ReentrantLock Lock2 = new ReentrantLock();
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
    public final ReentrantLock allQPPM = new ReentrantLock(); //pR, pp1, pp2, pp3, pp4, p, m
    public final ReentrantLock mainServer = new ReentrantLock();
    public final ReentrantLock activePlayerRefs = new ReentrantLock(); //MatchManager.activePlayerRefs
    public final ArrayList<ReentrantLock> actionL = new ArrayList<>();//Match.action, Match.loser

    private Locker(){}


    public static Locker getSafe() {
        return Safe;
    }

}
