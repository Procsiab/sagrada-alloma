package shared;

import java.io.Serializable;

public class Cell implements Serializable {

    public Integer value = 0;
    public char color = 'c';


    public Integer getValue() {
        return value;
    }

    public char getColor() {
        return color;
    }

}
