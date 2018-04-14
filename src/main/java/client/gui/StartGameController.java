package client.gui;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.util.Duration;
import shared.Match;
import shared.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StartGameController implements Initializable {
    final Text source = new Text(50, 100, "DRAG ME");
    final Text target = new Text(300, 100, "DROP HERE");
    private Match match;
    private List<Player> players;


    @FXML
    private GridPane PaneBackground;
    @FXML
    private GridPane PaneCarta;
    @FXML
    private ImageView CardMap;
    @FXML
    private Button TiraDadi;

    private void makeDraggable(Text Demo) {
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);

                event.consume();
            }
        });
    }

    public void initialize(URL location, ResourceBundle resources) {
        LoadBackground();
        BackGroundTransition();
        setCardMap();


    }

    @FXML
    private void TiraDadi(ActionEvent event) throws IOException {
        // QUI IL NUMERO DI DADI, COLORI E SPECIFICHE VA RECUPERATO DALLA LOGICA
        // TRAMITE UNA CHIAMATA MI RECAPITO I VALORI E DINAMICAMENTE LI ASSEGNO
        // AGLI OGGETTI ISTANZIATI

        Box box1 = new Box();
        box1.setWidth(100.0);
        box1.setHeight(100.0);
        box1.setDepth(100.0);
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        box1.setMaterial(redMaterial);
        Box box2 = new Box();
        box2.setWidth(100.0);
        box2.setHeight(100.0);
        box2.setDepth(100.0);
        box2.setTranslateX(100);
        target.setTranslateX(100);
        PhongMaterial greenMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.BLACK);
        box2.setMaterial(greenMaterial);
        PaneBackground.add(box1, 1, 1);
        PaneBackground.add(box2, 1, 1);
        PaneBackground.add(source, 1, 1);
        PaneBackground.add(target, 1, 1);
        makeDraggable(source);


    }


    private void setCardMap() {
        File file = new File("C:\\Users\\Mattia\\IdeaProjects\\InterfacciaDemo\\src\\Kaleidoscopic Dream.png");
        Image image = new Image(file.toURI().toString());
        CardMap.setImage(image);

        CardMap.fitHeightProperty();
        CardMap.setFitWidth(1280 / 3);

        PaneBackground.add(CardMap, 1, 2);
        PaneBackground.setGridLinesVisible(true);
        PaneCarta.setGridLinesVisible(true);

    }


    private void LoadBackground() {
        BackgroundImage myBI = new BackgroundImage(new Image("https://www.freevector.com/uploads/vector/preview/27785/Sagrada_Familia_Building.jpg", 1280, 800, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        PaneBackground.setBackground(new Background(myBI));


    }

    private void BackGroundTransition() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), PaneBackground);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ft.play();


    }

    public void updateView() {
        System.out.print("\"Hello\"");


    }
}
