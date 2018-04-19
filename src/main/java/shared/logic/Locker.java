package shared.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Locker {

    public static Locker safe = new Locker();
    public final ReentrantLock sLock1 = new ReentrantLock();
    public final ReentrantLock sLock2 = new ReentrantLock();
    public final ReentrantLock sLock3 = new ReentrantLock();
    public final List<ReentrantLock> sLock4 = new ArrayList<>();

    public final ReentrantLock cLock1 = new ReentrantLock();

    public Locker() {
    }


    public static Locker getSafe() {
        return safe;
    }

}

