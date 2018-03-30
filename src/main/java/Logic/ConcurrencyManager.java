package Logic;

import Logic.Concurrency.GeneralTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class ConcurrencyManager {


    private static ConcurrencyManager Manager = new ConcurrencyManager();
    public ExecutorService ThreadManager;

    private ConcurrencyManager(){
        this.ThreadManager = Executors.newCachedThreadPool();
    }

    public static ConcurrencyManager getManager() {
        return Manager;
    }

    public void submit(GeneralTask Task){
        ThreadManager.submit(Task);
    }
}



