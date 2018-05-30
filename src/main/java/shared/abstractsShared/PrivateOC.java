package shared.abstractsShared;

import java.io.Serializable;

public abstract class PrivateOC implements Serializable {

    private String name;
    private String description;
    private Character color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Character getColor() {
        return color;
    }

    public void setColor(Character color) {
        this.color = color;
    }
}