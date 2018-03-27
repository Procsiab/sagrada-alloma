package Logic;


import Network.mainServer;

public class Frame {

    private Window window;
    private Dice[][] dicePositions;

    public Dice[][] getDicePositions();
    boolean setDicePositions(Dice dice, Position position, Integer IDPlayer) {
        boolean ESITO = false;

        //check if position is on edge
        if (mainServer.getP().get(IDPlayer).getTurno() == 1) {
            if(position.getColumn()==0 || position.getColumn()==4){
                if(position.getRaw()==0)
                    ESITO = true;
                if
            }

        }

        //check whether in any adjacent position there is a previously placed dice or not

        //check if value or color fulfil place requirements

        //check if an orthogonally adjacent dice has same value or same color

    }
    public Window getWindow(){

    }
}
