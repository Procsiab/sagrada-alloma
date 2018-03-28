package Interface;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;

public class Interface extends Application {

    private final String TITLE = "Center in Stage";
    private final double WIDTH = 1280;
    private final double HEIGHT = 720;


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(TITLE);

        Image image = new Image(new FileInputStream("Sagrada.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(350);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        BorderPane pane = new BorderPane();
        pane.setCenter(imageView);
        pane.setId("pane");
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
        scaleTransition.setCycleCount(50);

        //Setting auto reverse value to true
        scaleTransition.setAutoReverse(false);

        //Playing the animation
        scaleTransition.play();

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}