package shared.abstracts;

import shared.*;

import java.io.Serializable;

public abstract class Window implements Serializable {

    public Cell[][] feature;
    public Integer tokens;
    private Dice[][] dicePositions;
    public Integer color;
    public Position lastPlacedPosition;

    public Cell[][] getFeature() {
        return feature;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setFeature(Cell[][] feature) {
        this.feature = feature;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }

public boolean setDicePosition(Dice dice, Position position, Player player){
        if(checkDicePosition(dice, position, player)) {
            player.overlay.dicePositions[position.row][position.column] = dice;
            return true;
        }
        else return false;

}

    public boolean checkDicePosition(Dice dice, Position position, Player player) {
        Integer esito = 0;
        Overlay overlay = player.overlay;

        //check if position is on edge
        if (player.turno == 1) {
            if(position.getRow()!=0 || position.getRow()!=3 || position.getColumn()!=0 || position.getColumn()!=4)
                return false;
        }

        //check whether in some adjacent dicePositions there is a previously placed dice or not complying specifics
        if(position.getRow() >= 0 && position.getColumn()-1 >= 0)
            if(overlay.dicePositions[position.getRow()][position.getColumn()-1]!= null)
                if(dice.getColor() != overlay.dicePositions[position.getRow()][position.getColumn()-1].getColor()&&
                        dice.getValue()!= overlay.dicePositions[position.getRow()][position.getColumn()-1].getValue())
                    return false;
                else
                    esito = 1;

        if(position.getRow()-1 >= 0 && position.getColumn()-1 >= 0)
            if(overlay.dicePositions[position.getRow()-1][position.getColumn()-1]!= null)
                esito = 1;

        if(position.getRow()-1 >= 0 && position.getColumn() >= 0)
            if(overlay.dicePositions[position.getRow()-1][position.getColumn()]!= null)
                if(dice.getColor() != overlay.dicePositions[position.getRow()-1][position.getColumn()].getColor()&&
                        dice.getValue()!= overlay.dicePositions[position.getRow()-1][position.getColumn()].getValue())
                    return false;
                else
                    esito = 1;

        if(position.getRow()-1 >= 0 && position.getColumn()+1 <= 4)
            if(overlay.dicePositions[position.getRow()-1][position.getColumn()+1]!= null)
                esito = 1;

        if(position.getRow() >= 0 && position.getColumn()+1 <= 4)
            if(overlay.dicePositions[position.getRow()][position.getColumn()+1]!= null)
                if(dice.getColor() != overlay.dicePositions[position.getRow()][position.getColumn()+1].getColor()&&
                        dice.getValue()!= overlay.dicePositions[position.getRow()][position.getColumn()+1].getValue())
                    return false;
                else
                    esito = 1;

        if(position.getRow()+1 <= 3 && position.getColumn()+1 <= 4)
            if(overlay.dicePositions[position.getRow()+1][position.getColumn()+1]!= null)
                esito = 1;

        if(position.getRow()+1 <= 3 && position.getColumn() >= 0)
            if(overlay.dicePositions[position.getRow()+1][position.getColumn()]!= null)
                if(dice.getColor() != overlay.dicePositions[position.getRow()+1][position.getColumn()].getColor()&&
                        dice.getValue()!= overlay.dicePositions[position.getRow()+1][position.getColumn()].getValue())
                    return false;
                else
                    esito = 1;

        if(position.getRow()+1 <= 3 && position.getColumn()-1 >= 0)
            if(overlay.dicePositions[position.getRow()+1][position.getColumn()-1]!= null)
                esito = 1;

        //check if value or color fulfil place requirements
        if(dice.getValue() != getFeature()[position.getRow()][position.getColumn()].getValue() ||
                dice.getColor() != getFeature()[position.getRow()][position.getColumn()].getColor()){
            return false;
        }


        //end
        if (esito == 1)
            return true;
        return false;

    }
}
