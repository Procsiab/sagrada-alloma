package client.gui;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.ref.WeakReference;
import java.util.Map;

public class StatusListCell extends ListCell<String> {

    private final Map<String, WeakReference<Image>> cache;
    private final ImageView imageView;

    public StatusListCell(Map<String, WeakReference<Image>> cache) {
        imageView = new ImageView();
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        setGraphic(imageView); // keep image even if empty for constant cell size
        this.cache = cache;
    }

    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText("");
            imageView.setImage(null); // don't display a image
        } else {
            cache.compute(item, (k, v) -> {
                // retrieve image or load
                Image img = null;
                if (v != null) {
                    img = v.get();
                }
                if (img == null) {
                    img = new Image(k);
                    v = new WeakReference<>(img);
                }

                // change image in imageview
                imageView.setImage(img);

                return v;
            });
            setText("a");
        }
    }

}