package shared.TransferObjects;

import java.io.Serializable;

public class ToolCT implements Serializable {
    public final String name;
    public final Integer tokensRequired;
    public final String description;

    public ToolCT(String name, Integer tokens, String description){
        this.name = name;
        this.tokensRequired = tokens;
        this.description = description;
    }
}
