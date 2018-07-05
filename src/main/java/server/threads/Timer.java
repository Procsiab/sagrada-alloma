package server.threads;

import server.concurrency.GeneralTask;

public class Timer extends GeneralTask {

    private final Integer time;
    private Boolean expired = false;
    private final Object obj;

    public Timer(Integer time, Object obj){
        this.time = time;
        this.obj = obj;
    }

    private synchronized void setExpired() {
        this.expired = true;
    }

    public synchronized Boolean getExpired() {
        return expired;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(time);
        }catch (InterruptedException ie){
            Thread.currentThread().interrupt();
        }

        setExpired();

        synchronized (obj) {
            obj.notifyAll();
        }
    }
}
