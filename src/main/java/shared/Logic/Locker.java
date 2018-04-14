package shared.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Locker {

    public static Locker safe = new Locker();
    public final ReentrantLock SLock1 = new ReentrantLock();
    public final List<ReentrantLock> SLock2 = new ArrayList<>(3);
    public final ReentrantLock SLock3 = new ReentrantLock();
    public final List<ReentrantLock> SLockA = new ArrayList<>();
    public final ReentrantLock SLockQ2 = new ReentrantLock();
    public final ReentrantLock SLockQ3 = new ReentrantLock();
    public final ReentrantLock SLockQ4 = new ReentrantLock();

    public final ReentrantLock CLock1 = new ReentrantLock();

    public Locker() {
    }


    public static Locker getSafe() {
        return safe;
    }

}

