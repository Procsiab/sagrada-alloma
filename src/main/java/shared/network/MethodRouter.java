package shared.network;

import shared.Cell;
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class MethodRouter implements Router {
    public MethodRouter() {
        super();
    }

    @Override
    public Object route(Object e, String methodName, Object[] argList) throws RemoteException {
        if (e == null) {
            throw new NullPointerException();
        }
        if (e instanceof SharedMiddlewareClient) {
            SharedMiddlewareClient o = (SharedMiddlewareClient) e;
            switch (methodName) {
                case "deniedAccess":
                    return o.deniedAccess();
                case "startGame":
                    return o.startGame((String) argList[0]);
                case "updateView":
                    o.updateView((GameManagerT) argList[0]);
                    break;
                case "chooseWindow":
                    return o.chooseWindow((ArrayList<Integer>) argList[0], (ArrayList<Cell[][]>) argList[1]);
                case "ping":
                    return o.ping();
                case "tavoloWin":
                    o.aPrioriWin();
                    break;
                case "enable":
                    o.enable();
                    break;
                case "shut":
                    o.shut();
                    break;
                case "printScore":
                    o.printScore((Integer) argList[0]);
                    break;
                case "setWinner":
                    o.setWinner();
                    break;
                case "chooseWindowBack":
                    return o.chooseWindowBack((Integer) argList[0]);
                case "startGameViewForced":
                    return o.startGameViewForced();
                case "placeDice":
                    return o.placeDice((Integer) argList[0], (Position) argList[1]);
                case "useToolC":
                    return o.useToolC((Integer) argList[0], (Position) argList[1], (Position) argList[2], (Position) argList[3], (Position) argList[4], (PositionR) argList[5], (Integer) argList[6], (Integer) argList[7]);
                case "exitGame2":
                    o.exitGame2();
                    break;
                case "endTurn":
                    o.endTurn();
                    break;
                case "updateViewFromC":
                    o.updateViewFromC();
                    break;
                case "exitGame1":
                    o.exitGame1();
                    break;
                default:
                    Logger.log("Requested wrong method " + methodName + " for interface SharedMiddlewareClient!");
                    break;
            }
        } else if (e instanceof SharedMiddlewareServer) {
            SharedMiddlewareServer o = (SharedMiddlewareServer) e;
            switch (methodName) {
                case "deniedAccess":
                    return o.deniedAccess((String) argList[0]);
                case "startGame":
                    return o.startGame((String) argList[0], (String) argList[1], (String) argList[2], (Integer) argList[3], (Boolean) argList[4]);
                case "updateView":
                    o.updateView((String) argList[0], (GameManagerT) argList[1]);
                    break;
                case "chooseWindow":
                    return o.chooseWindow((String) argList[0], (ArrayList<Integer>) argList[1], (ArrayList<Cell[][]>) argList[2]);
                case "ping":
                    return o.ping((String) argList[0]);
                case "tavoloWin":
                    o.tavoloWin((String) argList[0]);
                    break;
                case "enable":
                    o.enable((String) argList[0]);
                    break;
                case "shut":
                    o.shut((String) argList[0]);
                    break;
                case "printScore":
                    o.printScore((String) argList[0], (Integer) argList[1]);
                    break;
                case "setWinner":
                    o.setWinner((String) argList[0]);
                    break;
                case "chooseWindowBack":
                    return o.chooseWindowBack((String) argList[0], (Integer) argList[1]);
                case "startGameViewForced":
                    return o.startGameViewForced((String) argList[0]);
                case "placeDice":
                    return o.placeDice((String) argList[0], (Integer) argList[1], (Position) argList[2]);
                case "useToolC":
                    return o.useToolC((String) argList[0], (Integer) argList[1], (Position) argList[2], (Position) argList[3], (Position) argList[4], (Position) argList[5], (PositionR) argList[6], (Integer) argList[7], (Integer) argList[8]);
                case "exitGame2":
                    o.exitGame2((String) argList[0]);
                    break;
                case "endTurn":
                    o.endTurn((String) argList[0]);
                    break;
                case "updateViewFromC":
                    o.updateViewFromC((String) argList[0]);
                    break;
                case "exitGame1":
                    o.exitGame1((String) argList[0]);
                    break;
                default:
                    Logger.log("Requested wrong method " + methodName + " for interface SharedMiddlewareServer!");
                    break;
            }
        } else {
            Logger.log("Found exported object of wrong type: expected SharedMiddleware<Client|Server>");
        }
        return null;
    }
}
