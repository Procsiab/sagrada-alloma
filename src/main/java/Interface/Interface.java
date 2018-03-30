package Interface;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.FileInputStream;

public class Interface extends Application {
    private Scene scene, scene2;
    private final String TITLE = "Center in Stage";
    private final double WIDTH = 1280;
    private final double HEIGHT = 720;
    private final String IMAGE_PATH =
            "http://icons.iconarchive.com/icons/iconka/meow-2/64/cat-rascal-icon.png";

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(TITLE);
        // CARICAMENTO IMMAGINE BACKGROUND LOGIN
        Image image = new Image(new FileInputStream("C:\\Users\\Mattia\\IdeaProjects\\ids-2018\\src\\main\\java\\Interface\\Sagrada.png"));

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(350);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);
        // CARICAMENTO TERMINATO
        //CREAZIONE GRIGLIA BACKGROUND
        Image image2 = new Image(new FileInputStream("C:\\Users\\Mattia\\IdeaProjects\\InterfacciaDemo\\src\\SA01privateobjectivecardsFRONTv5_ITA_copia-page-001.jpg"));
        ImageView background = new ImageView(image2);
        // background.setRotate(90);

        background.setFitWidth(WIDTH);
        background.setFitHeight(HEIGHT);

        Button StartGame = new Button("Start Game");
        StartGame.setOnAction(e -> primaryStage.setScene(scene2));


        //Creating scale Transition
        ScaleTransition scaleTransition = new ScaleTransition();

        //Setting the duration for the transition
        scaleTransition.setDuration(Duration.millis(1000));

        //Setting the node for the transition
        scaleTransition.setNode(imageView);

        //Setting the dimensions for scaling
        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);

        //Setting the cycle count for the translation
        scaleTransition.setCycleCount(10);

        //Setting auto reverse value to true
        scaleTransition.setAutoReverse(false);

        //Playing the animation
        scaleTransition.play();
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(imageView,StartGame);
        layout1.setAlignment(Pos.CENTER);
        VBox layout2 = new VBox(20);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(background);
        scene = new Scene(layout1,WIDTH,HEIGHT);
        scene2 = new Scene(layout2,WIDTH,HEIGHT);
        // scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}