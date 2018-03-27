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
            if(position.getRaw()!=0 || position.getRaw()!=3 || position.getColumn()!=0 || position.getColumn()!=4)
                return false;
        }

        //check whether in some adjacent positions there is a previously placed dice or not
        if(position.getRaw()-1 >= 0 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRaw()-1][position.getColumn()-1]!= null)
                if(dice.getColor() != dicePositions[position.getRaw()-1][position.getColumn()-1].getColor())





        //check if value or color fulfil place requirements

        //check if an orthogonally adjacent dice has same value or same color

    }
    public Window getWindow(){

    }
}
