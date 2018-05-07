package shared;


import shared.Dice;

import java.io.Serializable;
import java.util.ArrayList;

public class RoundTrack implements Serializable {
    public Integer turn = 0;
    public ArrayList<ArrayList<Dice>> dices = new ArrayList<>(10);

    public RoundTrack(){
        for(int i = 1; i<10; i++){
            dices.add(new ArrayList<>());
        }
    }

}
