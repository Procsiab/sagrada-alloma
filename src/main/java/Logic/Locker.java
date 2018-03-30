package Logic;

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
    public final ReentrantLock action = new ReentrantLock();
    public final LinkedList<ReentrantLock> lockA = new LinkedList<ReentrantLock>();
    public final LinkedList<ReentrantLock> lockB = new LinkedList<ReentrantLock>();
    public final LinkedList<ReentrantLock> lockC = new LinkedList<ReentrantLock>();

    private Locker(){}


    public static Locker getSafe() {
        return Safe;
    }

    public void setLockA(Integer IDPlayer){
        lockA.add(IDPlayer, new ReentrantLock());
    }

    public void setLockB(Integer IDPlayer){
        lockB.add(IDPlayer, new ReentrantLock());
    }

    public void setLockC(Integer IDPlayer){
        lockC.add(IDPlayer, new ReentrantLock());
    }
}
