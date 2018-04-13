package client.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Locker {

    public static Locker safe = new Locker();
    public final ReentrantLock Lock1 = new ReentrantLock();
    public final List <ReentrantLock> Lock2 = new ArrayList<>(3);
    public final ReentrantLock Lock3 = new ReentrantLock();
    public final List<ReentrantLock> LockA = new ArrayList<>();

    public Locker(){}


    public static Locker getSafe() {
        return safe;
    }

}

