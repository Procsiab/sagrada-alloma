package client.gui;

import client.MainClient;
import client.ProxyClient;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.Dice;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.TransferObjects.PlayerT;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.*;

public class StartGameController implements Initializable {
    // Logic Variables
    private ProxyClient proxyClient = ProxyClient.getInstance();

    // FXML GUI Variables
    @FXML
    private GridPane paneBackground;
    @FXML
    private GridPane paneCarta0, paneCarta1, paneCarta2, paneCarta3;
    @FXML
    private Button placeDice, fineTurno, useToolCard, sendDataButton;
    @FXML
    private Button dice1, dice2, dice3, dice4, dice5, dice6, dice7, dice8, dice9;
    @FXML
    private Text numTokens;
    @FXML
    private ImageView toolCard1, toolCard2, toolCard3;
    @FXML
    private ImageView publicOC1, publicOC2, publicOC3;
    @FXML
    private ImageView privateOC;
    @FXML
    private AnchorPane roundTrack;
    @FXML
    private ComboBox comboBox1, comboBox2, comboBox3, comboBox4, comboBox5, comboBox6, comboBox7, comboBox8, comboBox9, comboBox10;
    @FXML
    private TextField changeValueField;

    // Utility Variables
    private int positionPoolDice, indexofToolCard;
    private Integer colIndex;
    private Integer rowIndex;
    private int incrementValue;
    private List<GridPane> listMapCard = new ArrayList<>();
    private ArrayList<Button> listDice = new ArrayList<>();
    private ArrayList<ImageView> listToolCard = new ArrayList<>();
    private ArrayList<ComboBox> listaComboBox = new ArrayList<>();
    private ArrayList<ImageView> listPublicOC = new ArrayList<>();

    private PositionR positionDiceRoundTrack = new PositionR();
    private Position positions[];
    private int counterPosition = 0;
    private int singleton=0;


    public StartGameController() {
        MainClient.setStartGameController(this);
    }


    public void initialize(URL location, ResourceBundle resources) {
        //loadBackground();
        // backGroundTransition();
        positions = new Position[4];
        initMapCards();
        initDice();
        initToolCards();
        initComboBox();
        initPublicOC();
        shut();
        initPool();

    }


    public void updateView(GameManagerT gameManager) {
        Platform.runLater(
                () -> {
                    System.out.print("I was updated, receiving the GameManager object:\n" + gameManager.toString());

                    clearPosizioni();
                    counterPosition = 0;
                    ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
                    int counterPosition = gameManager.pos;

                    if(singleton==0){
                        //Loading mapCards into view
                        addMapCards(gameManager);

                        // LOAD TOOLCARDS
                        loadToolCards(gameManager);

                        // LOAD PUBLIC OC
                        loadPublicOC(gameManager);

                        // LOAD PRIVATE OC
                        loadPrivateOC(gameManager);

                        singleton++;


                    }



                    // GET TOKENS
                    loadTokens(gameManager);

                    // LOAD POOL
                    loadPoolDice(gameManager);

                    // LOAD DICES INTO MAPS
                    loadDiceMaps(gameManager);

                    //LOAD DICES INTO ROUNDTRACK
                    loadDiceRoundTrack(gameManager);


                });
    }

    private void loadDiceRoundTrack(GameManagerT gameManager) {
        ArrayList<ArrayList<Dice>> roundTrackData = gameManager.roundTrack;
        final Map<String, WeakReference<Image>> cache = new HashMap<>();

        for (int h = 0; h < roundTrackData.size(); h++) {
            ComboBox<String> combo = listaComboBox.get(h);
            combo.setCellFactory(c -> new StatusListCell(cache));
            ObservableList<String> options = FXCollections.observableArrayList();

            List<Dice> testing = roundTrackData.get(h);

            for (Dice die : testing) {
                String color = Character.toString(die.getColor());
                String value = die.getValue().toString();
                String diceRound ="Dices/" + value + color + ".png";

                options.add(diceRound);
            }

            combo.setItems(options);
        }
    }

    private void loadDiceMaps(GameManagerT gameManager) {
        int counterPosition = gameManager.pos;
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;



        for (int i = 0; i < playersLocal.size(); i++) {


            if (counterPosition > playersLocal.size() - 1)
                counterPosition = 0;
            Dice[][] myOverlay = playersLocal.get(counterPosition).overlay;

            ObservableList<Node> myGrid = listMapCard.get(i).getChildren();
            int z = 0;
            for (int k = 0; k < 4; k++) {
                for (int y = 0; y < 5; y++) {


                    if (myOverlay[k][y] != null) {
                        char mycolor = myOverlay[k][y].getColor();
                        int mynumber = myOverlay[k][y].getValue();

                        myGrid.get(z).setStyle(("-fx-background-image: url('Dices/" + mynumber + "" + mycolor + ".png');-fx-background-size: 100% 100%;"));
                        myGrid.get(z).setOpacity(100);

                    } else {
                        myGrid.get(z).setStyle(("-fx-background-color: transparent;-fx-background-size: 100% 100%;"));

                    }


                    z++;

                }

            }
            counterPosition++;

        }
    }

    private void loadPrivateOC(GameManagerT gameManager) {
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        char namePrivateOC = playersLocal.get(gameManager.pos).privateO;
        Image image = new Image(namePrivateOC + ".png");
        privateOC.setImage(image);
    }

    private void loadPublicOC(GameManagerT gameManager) {
        for (int i = 0; i < gameManager.publicOCs.size(); i++) {
            String namePublicOC = gameManager.publicOCs.get(i);

            Image image = new Image("PublicOC/" + namePublicOC + ".png");

            listPublicOC.get(i).setImage(image);


        }

    }

    private void loadToolCards(GameManagerT gameManager) {
        for (int i = 0; i < gameManager.toolCards.size(); i++) {
            String nomeToolCard = gameManager.toolCards.get(i).name;

            Image image = new Image("ToolC/" + nomeToolCard + ".png");

            listToolCard.get(i).setImage(image);
        }


    }

    private void addMapCards(GameManagerT gameManager) {
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        int counterPosition = gameManager.pos;
        for (int i = 0; i < playersLocal.size(); i++) {
            String nomeCarta;


            if (counterPosition > playersLocal.size() - 1)
                counterPosition = 0;

            nomeCarta = playersLocal.get(counterPosition).window.getName();

            listMapCard.get(i).setStyle("-fx-background-image: url('Windows/" + nomeCarta + ".png');-fx-background-size: 100% 100%;");
            counterPosition++;

        }


    }

    private void loadTokens(GameManagerT gameManager) {
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        String numeroTokens = playersLocal.get(gameManager.pos).tokens.toString();
        numTokens.setText(numeroTokens);


    }

    private void loadPoolDice(GameManagerT gameManager) {
        int numDadi = gameManager.pool.size();
        for (int i = 0; i < numDadi; i++) {

            // INSERIRE EFFETIVO VALORE DEL DADO
            if (gameManager.pool.get(i) != null) {
                int numero = gameManager.pool.get(i).getValue();
                char color = gameManager.pool.get(i).getColor();

                listDice.get(i).setStyle("-fx-background-image: url('Dices/" + numero + "" + color + ".png');-fx-background-size: 100% 100%;");
            } else {
                listDice.get(i).setStyle("-fx-background-color: transparent;-fx-background-size: 100% 100%;");

            }

        }
        for (int i = numDadi; i < listDice.size(); i++) {
            listDice.get(i).setStyle("-fx-background-color: transparent;-fx-background-size: 100% 100%;");

        }


    }


    private void addPosition(int row, int column) {
        if (counterPosition < 4) {
            positions[counterPosition] = new Position(row, column);
            counterPosition++;
        } else {
            System.out.println("Superato limite!");

        }

    }

    public void shutdown() {
        proxyClient.exitGame2();


        // note that typically (i.e. if Platform.isImplicitExit() is true, which is the default)
        // closing the last open window will invoke Platform.exit() anyway
        Platform.exit();
    }


    // SUPPORT METHODS FOR THE MAIN ONES!


    private void initMapCards() {
        listMapCard.add(paneCarta0);
        listMapCard.add(paneCarta1);
        listMapCard.add(paneCarta2);
        listMapCard.add(paneCarta3);

    }

    private void initDice() {
        listDice.add(dice1);
        listDice.add(dice2);
        listDice.add(dice3);
        listDice.add(dice4);
        listDice.add(dice5);
        listDice.add(dice6);
        listDice.add(dice7);
        listDice.add(dice8);
        listDice.add(dice9);


    }

    private void initToolCards() {
        listToolCard.add(toolCard1);
        listToolCard.add(toolCard2);
        listToolCard.add(toolCard3);

    }

    private void initComboBox() {
        listaComboBox.add(comboBox1);
        listaComboBox.add(comboBox2);
        listaComboBox.add(comboBox3);
        listaComboBox.add(comboBox4);
        listaComboBox.add(comboBox5);
        listaComboBox.add(comboBox6);
        listaComboBox.add(comboBox7);
        listaComboBox.add(comboBox8);
        listaComboBox.add(comboBox9);
        listaComboBox.add(comboBox10);

    }

    private void initPublicOC() {
        listPublicOC.add(publicOC1);
        listPublicOC.add(publicOC2);
        listPublicOC.add(publicOC3);
    }

    private void initPool() {
        for (int i = 0; i < listDice.size(); i++) {
            listDice.get(i).setStyle("-fx-background-color: transparent;-fx-background-size: 100% 100%;");
        }
    }

    private void clearPosizioni() {
        for (int i = 0; i < positions.length; i++) {
            positions[i] = null;
        }
    }


    //FXML METHODS
    @FXML
    private void fineTurno(ActionEvent event) throws IOException {
        proxyClient.endTurn();

    }

    @FXML
    private void onClickMap(ActionEvent e) {
        Node source = (Node) e.getSource();
        colIndex = paneCarta0.getColumnIndex(source);
        rowIndex = paneCarta0.getRowIndex(source);
        addPosition(rowIndex, colIndex);
    }

    @FXML
    private void setSelectedDice(ActionEvent event) {
        Node selectedDice = (Node) event.getSource();
        String nomeDado = selectedDice.getId();
        positionPoolDice = listDice.indexOf(selectedDice);
    }

    @FXML
    private void placeDice(ActionEvent event) {

        Position diceGridPosition = new Position();
        diceGridPosition.setRow(rowIndex);
        diceGridPosition.setColumn(colIndex);

        Boolean placed = proxyClient.placeDice(positionPoolDice, diceGridPosition);
        if(placed==false){
            CustomAlert failedPlacement = new CustomAlert(Alert.AlertType.ERROR, "Error placing dice!" , "Dice placed in an unhautorized position!");

        }
        else proxyClient.updateViewFromC();
        clearPosizioni();

    }

    // CHANGE TO ABSOLUTE VALUES THE TRANSITION, TO AVOID BUGS WHEN THE USER KEEPS MOVING AROUND OVER THE CARDS
    @FXML
    private void zoomToolCard(MouseEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), (Node) event.getSource());
        st.setToX(3.5f);
        st.setToY(3.5f);
        st.setCycleCount(1);
        st.setAutoReverse(true);

        ((Node) event.getSource()).toFront();
        st.play();
    }

    @FXML
    private void zoomOutToolCard(MouseEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), (Node) event.getSource());
        st.setToX(1f);
        st.setToY(1f);
        st.setCycleCount(1);
        st.setAutoReverse(true);
        st.play();
    }

    @FXML
    private void zoomOC(MouseEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), (Node) event.getSource());
        st.setToX(3.5f);
        st.setToY(3.5f);
        st.setCycleCount(1);
        st.setAutoReverse(true);

        //Creating Translate Transition
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), (Node) event.getSource());



        //Setting the value of the transition along the x axis.
        translateTransition.setToX(100);
        translateTransition.setToY(-100);



        //Setting the cycle count for the transition
        translateTransition.setCycleCount(1);


        //Playing the animation

        ((Node) event.getSource()).toFront();
        ParallelTransition zoom = new ParallelTransition(st,translateTransition);
        zoom.play();
    }

    @FXML
    private void zoomOutOC(MouseEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), (Node) event.getSource());
        st.setToX(1f);
        st.setToY(1f);
        st.setCycleCount(1);
        st.setAutoReverse(true);

        //Creating Translate Transition
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), (Node) event.getSource());



        //Setting the value of the transition along the x axis.
        translateTransition.setToX(0);
        translateTransition.setToY(0);


        //Setting the cycle count for the transition
        translateTransition.setCycleCount(1);


        //Playing the animation

        ((Node) event.getSource()).toFront();
        ParallelTransition zoom = new ParallelTransition(st,translateTransition);
        zoom.play();
    }



    @FXML
    private void showRoundTrack(ActionEvent event) {
        if (roundTrack.isVisible() == true) {
            roundTrack.setVisible(false);
        } else
            roundTrack.setVisible(true);

    }

    @FXML
    private void selectedRoundDice(ActionEvent event) {
        int comboboxselected = listaComboBox.indexOf(event.getSource());
        int indextest = listaComboBox.get(comboboxselected).getSelectionModel().getSelectedIndex();
        positionDiceRoundTrack.setColumn(comboboxselected);
        positionDiceRoundTrack.setHeight(indextest);
    }

    @FXML
    private void selectToolCard(MouseEvent event) {
        indexofToolCard = listToolCard.indexOf(event.getSource());
    }

    @FXML
    private void useToolCard(ActionEvent event) {
        Boolean usedToolC = proxyClient.useToolC(indexofToolCard, positions[0], positions[1], positions[2], positions[3], positionDiceRoundTrack, positionPoolDice, incrementValue);
        if(usedToolC==false){
            CustomAlert failedUse = new CustomAlert(Alert.AlertType.ERROR, "Error using ToolCard!" , "Toolcard can't be used! Wrong parameters or violating rules!");

        }
        else proxyClient.updateViewFromC();

    }

    @FXML
    private void sendDataValue(ActionEvent event) {

        incrementValue = Integer.parseInt(changeValueField.getText());

    }

    //END OF FXML METHODS

    // METHODS CALLED FROM MIDDLEWARE

    public void enable() {
        for (int i = 0; i < listDice.size(); i++) {
            listDice.get(i).setDisable(false);

        }
        placeDice.setDisable(false);
        fineTurno.setDisable(false);
        useToolCard.setDisable(false);

    }


    public void printScore(ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) {

        Platform.runLater(
                () -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Winner.fxml"));
                    WinnerController winnerController = new WinnerController(nicks,scores,winner);
                    loader.setController(winnerController);


                    try{

                        Parent root = loader.load();
                        Scene startedGame = new Scene(root, 1280, 800, Color.WHITE);
                        Stage window = (Stage) paneCarta0.getScene().getWindow();
                        window.setScene(startedGame);
                        window.show();
                    }
                    catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                }
        );




    }


    public void shut() {
        for (int i = 0; i < listDice.size(); i++) {
            listDice.get(i).setDisable(true);

        }
        placeDice.setDisable(true);
        fineTurno.setDisable(true);
        useToolCard.setDisable(true);

    }

    public void aPrioriWin() {
        Platform.runLater(
                () -> {


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/WinnerAlone.fxml"));
                    try {
                        Parent root1 = loader.load();
                        Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
                        Stage window = (Stage) paneCarta0.getScene().getWindow();
                        window.setScene(startedGame);
                        window.show();
                    } catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                }
        );

    }
    public void onTimeStatus(String s1, String s2){
        Platform.runLater(
                () -> {
                    if(s2==null) {
                        CustomAlert connectionPlayers = new CustomAlert(Alert.AlertType.INFORMATION, "Connected players", "Connected: " + s1 + "\n" );
                    }
                    else {
                        CustomAlert connectionPlayers = new CustomAlert(Alert.AlertType.INFORMATION, "Disconnected players", "Disconnected: " + s2);
                    }
                });
    }
}
