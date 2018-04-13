package client.threads;

import client.logic.Locker;
import client.network.NetworkClient;
import shared.SharedClientGame;
import shared.SharedServerGameManager;
import shared.SharedServerMatchManager;

import java.util.concurrent.locks.ReentrantLock;

public class Game extends GeneralTask implements SharedClientGame {

    private Game game = this;
    private SharedServerMatchManager netMatchManager;
    private SharedServerGameManager netGameManager;
    private Integer nMates;
    private ReentrantLock Lock1 = new ReentrantLock();

    @Override
    public void run() {
        super.run();

        synchronized (Lock1){
            while (nMates == null)
                try {
                    Lock1.wait();
                }catch (InterruptedException e){
                e.printStackTrace();
                }
        }
        try {
            String string = netMatchManager.startGame(this, nMates);
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}