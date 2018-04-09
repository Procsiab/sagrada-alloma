package ClientP2P.Interface;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGameController implements Initializable {

    @FXML
    private GridPane PaneBackground;
    @FXML
    private GridPane PaneCarta;
    @FXML
    private ImageView CardMap;
    @FXML private Button TiraDadi;


    public void initialize(URL location, ResourceBundle resources) {
        LoadBackground();
        BackGroundTransition();
        setCardMap();

    }

    private void setCardMap() {
        File file = new File("C:\\Users\\Mattia\\IdeaProjects\\InterfacciaDemo\\src\\Kaleidoscopic Dream.png");
        Image image = new Image(file.toURI().toString());
        CardMap.setImage(image);

        CardMap.fitHeightProperty();
        CardMap.setFitWidth(1280/3);

        PaneBackground.add(CardMap,1,2);
        PaneBackground.setGridLinesVisible(true);
        PaneCarta.setGridLinesVisible(true);

    }


    private void LoadBackground() {
        BackgroundImage myBI = new BackgroundImage(new Image("https://www.freevector.com/uploads/vector/preview/27785/Sagrada_Familia_Building.jpg", 1280, 800, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        PaneBackground.setBackground(new Background(myBI));




    }

    private void BackGroundTransition(){
        FadeTransition ft = new FadeTransition(Duration.millis(1000), PaneBackground);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ft.play();




    }
}
