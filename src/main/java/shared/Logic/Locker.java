package shared.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Locker {

    public static Locker safe = new Locker();
    public final ReentrantLock SLock1 = new ReentrantLock();
    public final ReentrantLock SLock2 = new ReentrantLock();
    public final ReentrantLock SLock3 = new ReentrantLock();
    public final List<ReentrantLock> SLock4 = new ArrayList<>();

    public final ReentrantLock CLock1 = new ReentrantLock();

    public Locker() {
    }


    public static Locker getSafe() {
        return safe;
    }

}

