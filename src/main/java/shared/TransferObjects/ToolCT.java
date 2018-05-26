package shared.TransferObjects;

import java.io.Serializable;

public class ToolCT implements Serializable {
    public final String name;
    public final Integer tokensRequired;

    public ToolCT(String name, Integer tokens){
        this.name = name;
        this.tokensRequired = tokens;
    }
}
