package ClientP2P.Logic;


public class Frame {

    private Integer IDPlayer;
    private Window window;
    private Dice[][] dicePositions;
    private Locker Safe = Locker.getSafe();
    private Position lastPlaced;


    public Frame(Window window){
        this.window = window;
    }

    public Dice[][] getDicePositions() {
        return dicePositions;
    }

    public void setDicePositions(Dice dice, Position position, Integer IDPlayer) {
        synchronized (Safe.actionL) {
            Integer IDMatch = MatchManager.getInstance().getP().get(IDPlayer).getIDMatch();

            if (!checkDicePositions(dice, position, IDPlayer)) {
                //dice is not placed and player loses a chance
                MatchManager.getInstance().getM().get(IDMatch).setAction(1);
                return;
            }

            //put new dice in the final configuration
            dicePositions[position.getRow()][position.getColumn()] = dice;
            MatchManager.getInstance().getM().get(IDMatch).setAction(1);
            return;
        }
    }

    boolean checkDicePositions(Dice dice, Position position, Integer IDPlayer) {
        Integer ESITO = 0;

        //check if position is on edge
        if (MatchManager.getInstance().getP().get(IDPlayer).getTurno() == 1) {
            if(position.getRow()!=0 || position.getRow()!=3 || position.getColumn()!=0 || position.getColumn()!=4)
                return false;
        }

        //check whether in some adjacent positions there is a previously placed dice or not complying specifics
        if(position.getRow() >= 0 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRow()][position.getColumn()-1]!= null)
                if(dice.getColor() != dicePositions[position.getRow()][position.getColumn()-1].getColor()&&
                        dice.getValue()!= dicePositions[position.getRow()][position.getColumn()-1].getValue())
                    return false;
                else
                    ESITO = 1;

        if(position.getRow()-1 >= 0 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRow()-1][position.getColumn()-1]!= null)
                ESITO = 1;

        if(position.getRow()-1 >= 0 && position.getColumn() >= 0)
            if(dicePositions[position.getRow()-1][position.getColumn()]!= null)
                if(dice.getColor() != dicePositions[position.getRow()-1][position.getColumn()].getColor()&&
                        dice.getValue()!= dicePositions[position.getRow()-1][position.getColumn()].getValue())
                    return false;
                else
                    ESITO = 1;

        if(position.getRow()-1 >= 0 && position.getColumn()+1 <= 4)
            if(dicePositions[position.getRow()-1][position.getColumn()+1]!= null)
                ESITO = 1;

        if(position.getRow() >= 0 && position.getColumn()+1 <= 4)
            if(dicePositions[position.getRow()][position.getColumn()+1]!= null)
                if(dice.getColor() != dicePositions[position.getRow()][position.getColumn()+1].getColor()&&
                        dice.getValue()!= dicePositions[position.getRow()][position.getColumn()+1].getValue())
                    return false;
                else
                    ESITO = 1;

        if(position.getRow()+1 <= 3 && position.getColumn()+1 <= 4)
            if(dicePositions[position.getRow()+1][position.getColumn()+1]!= null)
                ESITO = 1;

        if(position.getRow()+1 <= 3 && position.getColumn() >= 0)
            if(dicePositions[position.getRow()+1][position.getColumn()]!= null)
                if(dice.getColor() != dicePositions[position.getRow()+1][position.getColumn()].getColor()&&
                        dice.getValue()!= dicePositions[position.getRow()+1][position.getColumn()].getValue())
                    return false;
                else
                    ESITO = 1;

        if(position.getRow()+1 <= 3 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRow()+1][position.getColumn()-1]!= null)
                ESITO = 1;

        //check if value or color fulfil place requirements
        if(dice.getValue() != window.getFeature()[position.getRow()][position.getColumn()].getValue() ||
                dice.getColor() != window.getFeature()[position.getRow()][position.getColumn()].getColor()){
            return false;
        }


        //end
        if (ESITO == 1)
            return true;
        return false;

    }

    public Window getWindow() {
        return window;
    }
}
