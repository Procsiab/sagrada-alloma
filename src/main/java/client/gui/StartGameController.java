package client.gui;

import client.MainClient;
import client.network.NetworkClient;
import client.threads.GameHelper;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

public class StartGameController implements SharedClientGame, Initializable {
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;
    private SharedServerMatchManager netMatchManager;
    private SharedServerGameManager netGameManager;
    public ArrayList<SharedServerPlayer> netPlayers = new ArrayList<>();
    private Integer nMates;
    private Integer nPlayer;
    private ReentrantLock lock1 = new ReentrantLock();
    private GameHelper game = MainClient.game;

    final Text source = new Text(50, 100, "DRAG ME");
    final Text target = new Text(300, 100, "DROP HERE");



    public StartGameController() {
        // Export the reference as UnicastRemoteObject
        NetworkClient.getInstance().remotize(this);
        // Obtain reference to remote MatchManager
        this.netMatchManager = NetworkClient.getInstance().getExportedObject("MatchManager");
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

    public void setNetPlayers(ArrayList<SharedServerPlayer> netPlayers) {
        this.netPlayers = netPlayers;
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


    }


    private void setCardMap() {
        File file = new File("Kaleidoscopic Dream.png");
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
        //fixedPlayer.get(id).posizionadado, e aggiornerà di per se le classi di riferimento di player e match
        //che stanno nel server.
    }

    @FXML
    private void fineTurno(ActionEvent event) throws IOException{
        System.out.print("\"Turno Finito\"");

    }

    @FXML
    private void handleMouseClicked(MouseEvent e){
        System.out.println("MouseEntered");
        Node source = (Node)e.getSource() ;
        System.out.println(source);

        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());

    }

    //TODO Implement the following methods

    @Override
    public void enable() {

    }

    @Override
    public boolean ping() {
        return false;
    }

    @Override
    public void chooseWindow(List<Integer> windows) {

    }

    @Override
    public void printScore(Integer score) {

    }

    @Override
    public void setNPlayer(Integer nPlayer) {

    }

    @Override
    public void shut() {

    }

    @Override
    public void aPrioriWin() {

    }
}
