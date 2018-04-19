package client.gui;
import client.MainClient;
import client.network.NetworkClient;
import client.threads.GameHelper;
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
import server.Player;
import shared.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

//TODO Implement all SharedGameClient methods
public class StartGameController implements Initializable, SharedClientGame {

    private SharedServerMatchManager netMatchManager;
    private SharedServerGameManager netGameManager;
    public ArrayList<Player> netPlayers = new ArrayList<>();
    private Integer nMates;
    private Integer nPlayer;
    private ReentrantLock Lock1 = new ReentrantLock();
    public static final String SERVER_IP = "localhost";
    public static final Integer RMI_PORT = 1099;
    public static final String RMI_IFACE_NAME = "Match";
    public static final Integer RMI_IFACE_PORT = 1100;
    public static final Integer SOCKET_PORT = 1101;
    protected String clientIp;
    protected Registry rmiRegistry;

    final Text source = new Text(50, 100, "DRAG ME");
    final Text target = new Text(300, 100, "DROP HERE");

    GameHelper game = MainClient.game;

    StartGameController(){
        NetworkClient.getInstance().remotize(this);
    }

    public void print(String s) {
        System.out.println(s);
    }

    @FXML
    private GridPane paneBackground;
    @FXML
    private GridPane paneCarta;
    @FXML
    private ImageView cardMap;
    @FXML
    private Button tiraDadi;

    public void setNetPlayers(ArrayList<Player> netPlayers) {
        this.netPlayers = netPlayers;
    }

    private void makeDraggable(Text demo) {
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
        loadBackground();
        backGroundTransition();
        setCardMap();


    }

    @FXML
    private void tiraDadi(ActionEvent event) throws IOException {
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
        paneBackground.add(box1, 1, 1);
        paneBackground.add(box2, 1, 1);
        paneBackground.add(source, 1, 1);
        paneBackground.add(target, 1, 1);
        makeDraggable(source);


    }


    private void setCardMap() {
        File file = new File("C:\\Users\\Mattia\\IdeaProjects\\InterfacciaDemo\\src\\Kaleidoscopic Dream.png");
        Image image = new Image(file.toURI().toString());
        cardMap.setImage(image);

        cardMap.fitHeightProperty();
        cardMap.setFitWidth(1280 / 3);

        paneBackground.add(cardMap, 1, 2);
        paneBackground.setGridLinesVisible(true);
        paneCarta.setGridLinesVisible(true);

    }


    private void loadBackground() {
        BackgroundImage myBI = new BackgroundImage(new Image("https://www.freevector.com/uploads/vector/preview/27785/Sagrada_Familia_Building.jpg", 1280, 800, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        paneBackground.setBackground(new Background(myBI));


    }

    private void backGroundTransition() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), paneBackground);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);

        ft.play();


    }

    public void updateView() {
        System.out.print("\"Hello\"");

        //avendo questi aggiorni la grafica all'inizio di ogni turno.
        //quando poi ad esempio l'utente chiama il metodo posizionadado, startgamecontroller chiama
        //game.posiziona dado, e aggiornerà di per se le classi di riferimento di player e match che sono
        //in GameHelper.
    }
    @FXML
    private void fineTurno(ActionEvent event) throws IOException{
        System.out.print("\"Turno Finito\"");


    }
}
