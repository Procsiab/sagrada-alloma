package client.gui;

import client.MainClient;
import client.threads.GameHelper;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.util.Duration;
import shared.*;
import shared.network.ConnectionNetwork;

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
    public ArrayList<SharedServerPlayer> netPlayers = new ArrayList<>();
    private Integer nMates;
    private Integer nPlayer;
    private ReentrantLock lock1 = new ReentrantLock();
    private GameHelper game = MainClient.game;
    private SharedServerMatchManager netMatchManager;
    private SharedServerGameManager netGameManager;

    final Text source = new Text(50, 100, "DRAG ME");
    final Text target = new Text(300, 100, "DROP HERE");



    public StartGameController() {
        this.netMatchManager = ConnectionNetwork.getConnection().getExported("MatchManager");
    }

    public void print(String s) {
        System.out.println(s);
    }

    @FXML
    private GridPane paneBackground;
    @FXML
    private GridPane paneCarta,paneCarta2,paneCarta3,paneCarta1;

    @FXML
    private ImageView cardMap;
    @FXML
    private Button tiraDadi;

    public void setWinner(){
        //start animation for the winner
    }

    public void setNetPlayers(ArrayList<SharedServerPlayer> netPlayers) {
        this.netPlayers = netPlayers;
    }

    public void setNetGameManager(SharedServerGameManager netGameManager) {
        this.netGameManager = netGameManager;
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
       paneCarta.setStyle("-fx-background-image: url('https://www.panorama.it/wp-content/uploads/2017/11/berlusconi-5-620x372.jpg');");
       paneCarta2.setStyle("-fx-background-image: url('https://i.guim.co.uk/img/media/f6afb130cb0d439635ed895df4ee65359ac8b38f/0_204_4016_2409/master/4016.jpg?w=300&q=55&auto=format&usm=12&fit=max&s=f468e7b14cd8fcf922e4214b464f5e9d');");
       paneCarta3.setStyle("-fx-background-image: url('https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Silvio_Berlusconi_1994.jpg/200px-Silvio_Berlusconi_1994.jpg');");
       paneCarta1.setStyle("-fx-background-image: url('https://media.gettyimages.com/photos/silvio-berlusconi-attends-che-tempo-che-fa-tv-show-on-november-26-in-picture-id879849778?s=612x612');");

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
    private void handleMouseClicked(ActionEvent e){
        System.out.println("MouseEntered");
        Node source = (Node)e.getSource() ;
        System.out.println(source);

        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        //System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());

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
