package shared.abstracts;


import shared.Dice;
import shared.Player;
import shared.Position;

import java.io.Serializable;

public abstract class Frame implements Serializable {

    public Window window;
    private Dice[][] dicePositions;
    public Integer color;
    public Position lastPlacedPosition;


    public Dice[][] getDicePositions() {
        return dicePositions;
    }

    public boolean setDicePositions(Dice dice, Position position, Player player) {

            if (!checkDicePositions(dice, position, player)) {
                //dice is not placed and player loses a chance
                return false;
            }

            //put new dice in the final configuration
            dicePositions[position.getRow()][position.getColumn()] = dice;
            return true;

    }

    public boolean checkDicePositions1(Dice dice, Position position, Player player) {

                //ignore color restriction
                Integer esito = 0;

                //check if position is on edge
                if (player.turno == 1) {
                    if (position.getRow() != 0 || position.getRow() != 3 || position.getColumn() != 0 || position.getColumn() != 4)
                        return false;
                }

                //check whether in some adjacent positions there is a previously placed dice or not complying specifics
                if (position.getRow() >= 0 && position.getColumn() - 1 >= 0)
                    if (dicePositions[position.getRow()][position.getColumn() - 1] != null)
                        if (dice.getColor() != dicePositions[position.getRow()][position.getColumn() - 1].getColor() &&
                                dice.getValue() != dicePositions[position.getRow()][position.getColumn() - 1].getValue())
                            return false;
                        else
                            esito = 1;

                if (position.getRow() - 1 >= 0 && position.getColumn() - 1 >= 0)
                    if (dicePositions[position.getRow() - 1][position.getColumn() - 1] != null)
                        esito = 1;

                if (position.getRow() - 1 >= 0 && position.getColumn() >= 0)
                    if (dicePositions[position.getRow() - 1][position.getColumn()] != null)
                        if (dice.getColor() != dicePositions[position.getRow() - 1][position.getColumn()].getColor() &&
                                dice.getValue() != dicePositions[position.getRow() - 1][position.getColumn()].getValue())
                            return false;
                        else
                            esito = 1;

                if (position.getRow() - 1 >= 0 && position.getColumn() + 1 <= 4)
                    if (dicePositions[position.getRow() - 1][position.getColumn() + 1] != null)
                        esito = 1;

                if (position.getRow() >= 0 && position.getColumn() + 1 <= 4)
                    if (dicePositions[position.getRow()][position.getColumn() + 1] != null)
                        if (dice.getColor() != dicePositions[position.getRow()][position.getColumn() + 1].getColor() &&
                                dice.getValue() != dicePositions[position.getRow()][position.getColumn() + 1].getValue())
                            return false;
                        else
                            esito = 1;

                if (position.getRow() + 1 <= 3 && position.getColumn() + 1 <= 4)
                    if (dicePositions[position.getRow() + 1][position.getColumn() + 1] != null)
                        esito = 1;

                if (position.getRow() + 1 <= 3 && position.getColumn() >= 0)
                    if (dicePositions[position.getRow() + 1][position.getColumn()] != null)
                        if (dice.getColor() != dicePositions[position.getRow() + 1][position.getColumn()].getColor() &&
                                dice.getValue() != dicePositions[position.getRow() + 1][position.getColumn()].getValue())
                            return false;
                        else
                            esito = 1;

                if (position.getRow() + 1 <= 3 && position.getColumn() - 1 >= 0)
                    if (dicePositions[position.getRow() + 1][position.getColumn() - 1] != null)
                        esito = 1;

                //check if value or color fulfil place requirements
                if (dice.getValue() != window.getFeature()[position.getRow()][position.getColumn()].getValue()) {
                    return false;
                }


                //end
                if (esito == 1)
                    return true;
                return false;
    }

    public boolean checkDicePositions2(Dice dice, Position position, Player player){
        //ignore shade restriction
        return true;
    }



    public boolean checkDicePositions(Dice dice, Position position, Player player) {
        Integer esito = 0;

        //check if position is on edge
        if (player.turno == 1) {
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
                    esito = 1;

        if(position.getRow()-1 >= 0 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRow()-1][position.getColumn()-1]!= null)
                esito = 1;

        if(position.getRow()-1 >= 0 && position.getColumn() >= 0)
            if(dicePositions[position.getRow()-1][position.getColumn()]!= null)
                if(dice.getColor() != dicePositions[position.getRow()-1][position.getColumn()].getColor()&&
                        dice.getValue()!= dicePositions[position.getRow()-1][position.getColumn()].getValue())
                    return false;
                else
                    esito = 1;

        if(position.getRow()-1 >= 0 && position.getColumn()+1 <= 4)
            if(dicePositions[position.getRow()-1][position.getColumn()+1]!= null)
                esito = 1;

        if(position.getRow() >= 0 && position.getColumn()+1 <= 4)
            if(dicePositions[position.getRow()][position.getColumn()+1]!= null)
                if(dice.getColor() != dicePositions[position.getRow()][position.getColumn()+1].getColor()&&
                        dice.getValue()!= dicePositions[position.getRow()][position.getColumn()+1].getValue())
                    return false;
                else
                    esito = 1;

        if(position.getRow()+1 <= 3 && position.getColumn()+1 <= 4)
            if(dicePositions[position.getRow()+1][position.getColumn()+1]!= null)
                esito = 1;

        if(position.getRow()+1 <= 3 && position.getColumn() >= 0)
            if(dicePositions[position.getRow()+1][position.getColumn()]!= null)
                if(dice.getColor() != dicePositions[position.getRow()+1][position.getColumn()].getColor()&&
                        dice.getValue()!= dicePositions[position.getRow()+1][position.getColumn()].getValue())
                    return false;
                else
                    esito = 1;

        if(position.getRow()+1 <= 3 && position.getColumn()-1 >= 0)
            if(dicePositions[position.getRow()+1][position.getColumn()-1]!= null)
                esito = 1;

        //check if value or color fulfil place requirements
        if(dice.getValue() != window.getFeature()[position.getRow()][position.getColumn()].getValue() ||
                dice.getColor() != window.getFeature()[position.getRow()][position.getColumn()].getColor()){
            return false;
        }


        //end
        if (esito == 1)
            return true;
        return false;

    }

    public Window getWindow() {
        return window;
    }
}
