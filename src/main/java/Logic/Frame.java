package Logic;


import Network.mainServer;

public class Frame {

    private Window window;
    private Dice[][] dicePositions;

    public Dice[][] getDicePositions() {
        return dicePositions;
    }

    public void setDicePositions(Dice dice, Position position, Integer IDPlayer){
        if(!checkDicePositions(dice, position, IDPlayer)) {
            //dice is not placed and player loses a chance
            return ;
        }

        //put new dice in the final configuration
            dicePositions[position.getRaw()][position.getColumn()] = dice;
    }

    boolean checkDicePositions(Dice dice, Position position, Integer IDPlayer) {
        Integer ESITO = 1;

        //check if position is on edge
        if (mainServer.getP().get(IDPlayer).getTurno() == 1) {
            if(position.getRaw()!=0 || position.getRaw()!=3 || position.getColumn()!=0 || position.getColumn()!=4)
                return false;
        }

        //check whether in some adjacent positions there is a previously placed dice or not complying specifics
        if(position.getRaw() >= 0 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRaw()][position.getColumn()-1]!= null)
                if(dice.getColor() != dicePositions[position.getRaw()][position.getColumn()-1].getColor()&&
                        dice.getValue()!= dicePositions[position.getRaw()][position.getColumn()-1].getValue())
                    return false;
                else
                    ESITO = 0*ESITO;

        if(position.getRaw()-1 >= 0 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRaw()-1][position.getColumn()-1]!= null)
                ESITO = 0*ESITO;

        if(position.getRaw()-1 >= 0 && position.getColumn() >= 0)
            if(dicePositions[position.getRaw()-1][position.getColumn()]!= null)
                if(dice.getColor() != dicePositions[position.getRaw()-1][position.getColumn()].getColor()&&
                        dice.getValue()!= dicePositions[position.getRaw()-1][position.getColumn()].getValue())
                    return false;
                else
                    ESITO = 0*ESITO;

        if(position.getRaw()-1 >= 0 && position.getColumn()+1 >= 0)
            if(dicePositions[position.getRaw()-1][position.getColumn()+1]!= null)
                ESITO = 0*ESITO;

        if(position.getRaw() >= 0 && position.getColumn()+1 >= 0)
            if(dicePositions[position.getRaw()][position.getColumn()+1]!= null)
                if(dice.getColor() != dicePositions[position.getRaw()][position.getColumn()+1].getColor()&&
                        dice.getValue()!= dicePositions[position.getRaw()][position.getColumn()+1].getValue())
                    return false;
                else
                    ESITO = 0*ESITO;

        if(position.getRaw()+1 >= 0 && position.getColumn()+1 >= 0)
            if(dicePositions[position.getRaw()+1][position.getColumn()+1]!= null)
                ESITO = 0*ESITO;

        if(position.getRaw()+1 >= 0 && position.getColumn() >= 0)
            if(dicePositions[position.getRaw()+1][position.getColumn()]!= null)
                if(dice.getColor() != dicePositions[position.getRaw()+1][position.getColumn()].getColor()&&
                        dice.getValue()!= dicePositions[position.getRaw()+1][position.getColumn()].getValue())
                    return false;
                else
                    ESITO = 0*ESITO;

        if(position.getRaw()+1 >= 0 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRaw()+1][position.getColumn()-1]!= null)
                ESITO = 0*ESITO;

        //check if value or color fulfil place requirements
        if(dice.getValue() != window.getFeature()[position.getRaw()][position.getColumn()].getValue() ||
                dice.getColor() != window.getFeature()[position.getRaw()][position.getColumn()].getColor()){
            return false;
        }


        //end
        if (ESITO == 1)
            return false;
        else
            return true;

    }

    public Window getWindow() {
        return window;
    }
}
