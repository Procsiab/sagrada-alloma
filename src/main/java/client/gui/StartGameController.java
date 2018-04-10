package client.gui;

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
    private GridPane paneBackground;
    @FXML
    private GridPane paneCarta;
    @FXML
    private ImageView cardMap;
    @FXML private Button tiraDadi;


    public void initialize(URL location, ResourceBundle resources) {
        loadBackground();
        backGroundTransition();
        setCardMap();

    }

    private void setCardMap() {
        File file = new File("C:\\Users\\Mattia\\IdeaProjects\\InterfacciaDemo\\src\\Kaleidoscopic Dream.png");
        Image image = new Image(file.toURI().toString());
        cardMap.setImage(image);

        cardMap.fitHeightProperty();
        cardMap.setFitWidth(1280/3);

        paneBackground.add(cardMap,1,2);
        paneBackground.setGridLinesVisible(true);
        paneCarta.setGridLinesVisible(true);

    }


    private void loadBackground() {
        BackgroundImage myBI = new BackgroundImage(new Image("https://www.freevector.com/uploads/vector/preview/27785/Sagrada_Familia_Building.jpg", 1280, 800, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        paneBackground.setBackground(new Background(myBI));




    }

    private void backGroundTransition(){
        FadeTransition ft = new FadeTransition(Duration.millis(1000), paneBackground);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ft.play();




    }
}
