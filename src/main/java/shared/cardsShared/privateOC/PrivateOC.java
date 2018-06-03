package shared.cardsShared.privateOC;

public class PrivateOC {
    private String name;
    private String description;
    private Character color;

    public PrivateOC(Character ch) {
        this.setColor(ch);
    }

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