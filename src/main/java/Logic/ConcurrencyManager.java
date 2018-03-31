package Logic;

import Logic.Concurrency.GeneralTask;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class ConcurrencyManager {

    //How to use ConcurrencyManager?
    //1 create new class of your Task extending GeneralTask. You may add methods, variables and contructor to this class.
    //2 instantiate this class giving the constructor the variables to initialize (if any):
    //  ex: TurnManager turnM = new TurnManager(att1, att2)
    //3 Put in run() the executable code of your thread. (Keep in mind, thread finishes when last instruction in run()
    //  is executed.
    //4 call ConcurrencyManager.submit(turnM)


    //public final static ConcurrencyManager Manager = new ConcurrencyManager();
    public static final ExecutorService ThreadManager = Executors.newCachedThreadPool();

    private ConcurrencyManager(){
    }

    public static void submit(GeneralTask Task){
        ThreadManager.submit(Task);
    }
}



