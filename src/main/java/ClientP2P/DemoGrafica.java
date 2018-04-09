package ClientP2P;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class DemoGrafica extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        Scene LogIn = new Scene(root);
        primaryStage.setScene(LogIn);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}