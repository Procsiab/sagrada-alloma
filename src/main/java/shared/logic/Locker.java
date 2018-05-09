package shared.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Locker implements Serializable {

    public static Locker safe = new Locker();
    public final ReentrantLock sLock1 = new ReentrantLock();
    public final ReentrantLock sLock2 = new ReentrantLock(); //queue MatchManager q
    public final ReentrantLock sLock3 = new ReentrantLock();
    public final List<ReentrantLock> sLock4 = new ArrayList<>();

    public final ReentrantLock cLock1 = new ReentrantLock();

    public Locker() {
        super();
    }


    public static Locker getSafe() {
        return safe;
    }

}

