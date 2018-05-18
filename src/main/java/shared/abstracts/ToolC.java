package shared.abstracts;

import java.io.Serializable;

public abstract class ToolC implements Serializable {


    //public Player player; ?
    public boolean checkIfEmpty(){
        //give positions and overlay
        //check if positions are empty. You can't place a die on another
        return true;
    }

    public boolean checkIfAble(){
        //check if tokens of the player is ge than those required from card
        return true;
    }
}
