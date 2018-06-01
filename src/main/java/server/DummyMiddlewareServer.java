package server;

import server.threads.GameManager;
import shared.Cell;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.network.Connection;
import shared.network.SharedMiddlewareServer;
import shared.network.rmi.NetworkRmi;
import shared.network.socket.NetworkSocket;

import java.util.ArrayList;

public class DummyMiddlewareServer implements SharedMiddlewareServer {
    private static final String SERVER_INTERFACE = "DummyMiddlewareServer";
    private static DummyMiddlewareServer instance = new DummyMiddlewareServer();


    public static DummyMiddlewareServer getInstance() {
        return instance;
    }

    private DummyMiddlewareServer(){}

    @Override
    public Boolean deniedAccess(String uuid) {
        try {
            GameManager game = SReferences.getGameRefEnhanced(uuid);
            String expected = game.getExpected();

            if (expected.equals("all"))
                return false;
            else if (expected.equals("none")) {
                Logger.log("Denied access for UUID " + uuid);
                return true;
            } else {
                Logger.log("Denied access for UUID " + uuid);
                return !expected.equals(uuid);
            }
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
        }
        return true;
    }

    @Override
    public String startGame(String uuid, String ip, Integer port, Boolean isSocket) {
        return MatchManager.getInstance().startGame(uuid, ip, port, isSocket);
    }

    @Override
    public void updateView(String uuid, GameManagerT gameManager) {
        return;
    }

    @Override
    public Boolean chooseWindow(String uuid, ArrayList<Integer> windows, ArrayList<Cell[][]> matrices) {
        return true;
    }

    @Override
    public Boolean ping(String uuid) {
        return true;
    }

    @Override
    public void tavoloWin(String uuid) {
        return;
    }

    @Override
    public void enable(String uuid) {
        return;
    }

    @Override
    public void shut(String uuid) {
        return;
    }

    @Override
    public void printScore(String uuid, Integer score) {
        return;
    }

    @Override
    public void setWinner(String uuid) {
        return;
    }

    public Boolean chooseWindowBack(String uuid, Integer window) {

        try {
            if (deniedAccess(uuid)) {
                return false;
            }
            return SReferences.getPlayerRefEnhanced(uuid).setWindowFromC(window - 1);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    public Boolean startGameViewForced(String uuid) {
        return true;
    }

    public Boolean placeDice(String uuid, Integer index, Position p) {
        try {
            if (deniedAccess(uuid))
                return false;
            return SReferences.getPlayerRefEnhanced(uuid).placeDice(index, p);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    @Override
    public Boolean useToolC(String uuid, Integer i1, Position p1, Position p2, Position p3, Position p4, PositionR pr, Integer i2, Integer i3) {
        try {
            if (deniedAccess(uuid))
                return false;
            return SReferences.getPlayerRefEnhanced(uuid).useToolC(i1, p1, p2, p3, p4, pr, i2, i3);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }

    @Override
    public void exitGame2(String uuid) {
        try {
            SReferences.getGameRefEnhanced(uuid).exitGame2(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
    }

    @Override
    public void endTurn(String uuid) {
        try {
            if (deniedAccess(uuid))
                return;
            SReferences.getGameRefEnhanced(uuid).endTurn(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
    }

    @Override
    public void updateViewFromC(String uuid) {
        try {
            if (deniedAccess(uuid))
                return;
            SReferences.getGameRefEnhanced(uuid).updateView(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
    }

    public Boolean exitGame1(String uuid) {
        try {
            return MatchManager.exitGame1(uuid);
        } catch (NullPointerException npe) {
            Logger.log("Unable to find player with UUID " + uuid);
            Logger.strace(npe);
        }
        return false;
    }
}