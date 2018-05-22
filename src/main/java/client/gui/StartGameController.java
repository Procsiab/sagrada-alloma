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
import javafx.scene.text.Text;
import javafx.util.Duration;
import server.threads.GameManager;
import shared.TransferObjects.GameManagerT;
import shared.TransferObjects.PlayerT;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

public class StartGameController implements Initializable {
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;
    private GameManager netGameManager;
    private Integer nMates;
    private Integer nPlayer;
    private ReentrantLock lock1 = new ReentrantLock();
    private GameHelper game = MainClient.game;



    //todo for each method check if you are denied accessing server

    public StartGameController() {
        MainClient.startGameController = this;
        //TODO Start server connector class
    }

    public void print(String s) {
        System.out.println(s);
    }

    @FXML
    private GridPane paneBackground;
    @FXML
    private GridPane paneCarta0,paneCarta1,paneCarta2,paneCarta3;
    private ArrayList<GridPane> listaGriglie = new ArrayList<>();

    @FXML
    private ImageView cardMap;
    @FXML
    private Button tiraDadi;
    @FXML
    private Button dice1,dice2,dice3,dice4,dice5,dice6,dice7,dice8,dice9;

    @FXML
    private ArrayList<Button> listaDadi = new ArrayList<>();
    @FXML
    private Text numTokens;


    public void setWinner(){
        //start animation for the winner
    }

    public void setNetGameManager(GameManager netGameManager) {
        this.netGameManager = netGameManager;
    }

    public void initialize(URL location, ResourceBundle resources) {
        loadBackground();
        backGroundTransition();
        //TODO : FAR FUNZIONARE loadArray
        loadArray();
        loadDadi();
        // setCardMap();
    }

    private void loadArray(){
        listaGriglie.add(paneCarta0);
        listaGriglie.add(paneCarta1);
        listaGriglie.add(paneCarta2);
        listaGriglie.add(paneCarta3);

    }


    private void loadDadi(){
        listaDadi.add(dice1);
        listaDadi.add(dice2);
        listaDadi.add(dice3);
        listaDadi.add(dice4);
        listaDadi.add(dice5);
        listaDadi.add(dice6);
        listaDadi.add(dice7);
        listaDadi.add(dice8);
        listaDadi.add(dice9);



    }



   /* private void setCardMap() {
       paneCarta0.setStyle("-fx-background-image: url('1.png');-fx-background-size: 100% 100%;");
       paneCarta1.setStyle("-fx-background-image: url('2.png');-fx-background-size: 100% 100%;");
       paneCarta2.setStyle("-fx-background-image: url('3.png');-fx-background-size: 100% 100%;");
       paneCarta3.setStyle("-fx-background-image: url('4.png');-fx-background-size: 100% 100%;");

    } */


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

    public void updateView(GameManagerT gameManager) {
        System.out.print("I was updated, receiving the GameManager object:\n" + gameManager.toString());
        String nomeCarta,numeroTokens;
        int numDadi;
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        int numberofplayer = playersLocal.size();
        int counterPosition = gameManager.pos;
        System.out.println("Valore counterPosition:" + counterPosition);
        System.out.println("Valore gameManager:" + gameManager.pos);
        for (int i = 0; i < playersLocal.size(); i++) {
            System.out.println("Valore counterPosition dentro al ciclo:" + counterPosition);
            System.out.println("Valore gameManager dentro al ciclo:" + gameManager.pos);

            if(counterPosition>playersLocal.size()-1)
                counterPosition=0;
            System.out.println("Valore counterPosition dentro al ciclo dopo reset :" + counterPosition);
            System.out.println(playersLocal.get(counterPosition).window.getNome());
            nomeCarta = playersLocal.get(counterPosition).window.getNome();
            //TODO : INSERIRE LA CARTA EFFETTIVA. PRENDERE DALL'ARRAY DELLE CARTE SCELTE DA GAME MANAGER LA CARTA
            // INSERIRE IL NOME DELLA FINESTRA AL POSTO DI WINDOW 1. RECUPERARLO DAL PLAYER T. CHIEDERE COME
            listaGriglie.get(i).setStyle("-fx-background-image: url('"+nomeCarta+".png');-fx-background-size: 100% 100%;");
            counterPosition++;

        }
        // GET TOKENS
        numeroTokens = playersLocal.get(gameManager.pos).tokens.toString();
        numTokens.setText(numeroTokens);
        //avendo questi aggiorni la grafica all'inizio di ogni turno.
        //quando poi ad esempio l'utente chiama il metodo posizionadado, startgamecontroller chiama
        //fixedPlayer.get(id).posizionadado, e aggiornerà di per se le classi di riferimento di player e match
        //che stanno nel server.

        // SET DADI POOL
         numDadi= gameManager.pool.size();
        System.out.println("Numero di dadi :" + numDadi);
        for (int i = 0; i < numDadi; i++){
            System.out.println("Valore di i nel ciclo:" + i);
            // INSERIRE EFFETIVO VALORE DEL DADO
            listaDadi.get(i).setStyle("-fx-background-image: url('1blue.png');-fx-background-size: 100% 100%;");

        }





    }

    @FXML
    private void fineTurno(ActionEvent event) throws IOException{
        System.out.print("\"Turno Finito\"");
        dice1.setStyle("-fx-background-image: url('1blue.png');-fx-background-size: 100% 100%;");
        dice2.setStyle("-fx-background-image: url('2red.png');-fx-background-size: 100% 100%;");
        dice3.setStyle("-fx-background-image: url('4yellow.png');-fx-background-size: 100% 100%;");
        dice4.setStyle("-fx-background-image: url('6purple.png');-fx-background-size: 100% 100%;");
        dice5.setStyle("-fx-background-image: url('3green.png');-fx-background-size: 100% 100%;");
        dice6.setStyle("-fx-background-image: url('1blue.png');-fx-background-size: 100% 100%;");
        dice7.setStyle("-fx-background-image: url('1red.png');-fx-background-size: 100% 100%;");
        dice8.setStyle("-fx-background-image: url('1blue.png');-fx-background-size: 100% 100%;");




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

    public void diceClicked(ActionEvent e){

    }

    //TODO Implement the following methods

    public void enable() {

    }

    public boolean ping() {
        return false;
    }



    public void printScore(Integer score) {

    }

    public void setNPlayer(Integer nPlayer) {

    }

    public void shut() {

    }

    public void aPrioriWin() {

    }
}
