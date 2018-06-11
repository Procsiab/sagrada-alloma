package client.gui;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StatusListCell extends ListCell<String> {
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println("E' NULL IL RIEMPIMENTO");

        setGraphic(null);
        setText(null);
        if (item != null) {
            System.out.println("NON E' NULL IL RIEMPIMENTO");

            ImageView imageView = new ImageView(new Image(item));
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            setGraphic(imageView);
            setText("a");
        }
    }
}

