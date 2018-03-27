package Logic;


import Network.mainServer;

public class Frame {

    private Window window;
    private Dice[][] dicePositions;

    public Dice[][] getDicePositions();
    boolean setDicePositions(Dice dice, Position position, Integer IDPlayer) {
        boolean ESITO = false;

        //check if position is on edge
        if (mainServer.get.get(IDPlayer) == 1) {
            //check
        }

        //check whether in any adjacent position there is a previously placed dice or not

        //check if value or color fulfil place requirements

        //check if an orthogonally adjacent dice has same value or same color

    }
    public Window getWindow(){

    }
}
