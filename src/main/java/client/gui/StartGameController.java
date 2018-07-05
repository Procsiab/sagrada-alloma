package client.gui;

import client.MainClient;
import client.ProxyClient;
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
import shared.Logger;
import shared.Position;
import shared.PositionR;
import shared.TransferObjects.GameManagerT;
import shared.TransferObjects.PlayerT;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.*;

/**
    This class is the controller associated with {@code StartGame.fxml}. This class manages the whole GUI gameplay. It loads all the MapCards, Pool Dices, ToolCards and Objective Cards.
    Refreshes the view with the dice placed from all users.
 */
public class StartGameController implements Initializable {
    // Logic Variables
    private ProxyClient proxyClient = ProxyClient.getInstance();

    // FXML GUI Variables
    @FXML
    private GridPane paneCarta0, paneCarta1, paneCarta2, paneCarta3;
    @FXML
    private Button placeDice, fineTurno, useToolCard;
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
    private int counterPositionGame = 0;
    private int singleton=0;
    private String sizeBackground = ".png');-fx-background-size: 100% 100%;";
    private String transparentBackround = "-fx-background-color: transparent;-fx-background-size: 100% 100%;";


    public StartGameController() {
        MainClient.setStartGameController(this);
    }

    /**
     Initialization of all arrays needed to load the views. Arrays are used to map 1 on 1 the elements from the graphics with the element of the model
     */
    public void initialize(URL location, ResourceBundle resources) {
        positions = new Position[4];
        initMapCards();
        initDice();
        initToolCards();
        initComboBox();
        initPublicOC();
        shut();
        initPool();

    }
    /**
     {@code updateView} is called from {@link ProxyClient#updateView(GameManagerT)} and receives a {@link GameManagerT} as parameter. {@code GameManagerT} contains all the data
     regarding the current match, such as players: {@link GameManagerT#vPlayers}, ToolCards: {@link GameManagerT#toolCards} ecc...
     {@code updateView} access the data in {@link GameManagerT} in order to create/update the elements in the graphic interface.
     The variable {@code singleton} is used to improve performance and speed, once the elements that don't change during the game are loaded at first (such as Mapcards,ToolCards, ecc...),
     it's useless to reload them again. {@code singleton} allows the program to run the code in the {@code if} statement only a single time.
     */

    public void updateView(GameManagerT gameManager) {
        Platform.runLater(
                () -> {
                    Logger.log("I was updated, receiving the GameManager object:\n" + gameManager.toString());

                    clearPosizioni();
                    counterPositionGame = 0;

                    if(singleton==0){
                        // LOAD MAPCARDS
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

    /**
     Loads the image associated with the mapcard of each player. The mapcard associated with the current player is placed at the bottom of the view, the others are loaded counterclockwise
     */
    private void addMapCards(GameManagerT gameManager) {
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        int counterPosition = gameManager.pos;
        for (int i = 0; i < playersLocal.size(); i++) {
            String nomeCarta;


            if (counterPosition > playersLocal.size() - 1)
                counterPosition = 0;

            nomeCarta = playersLocal.get(counterPosition).window.getName();

            listMapCard.get(i).setStyle("-fx-background-image: url('Windows/" + nomeCarta + sizeBackground);
            counterPosition++;

        }


    }

    /**
     Loads the images associated with the relative ToolCards
     */
    private void loadToolCards(GameManagerT gameManager) {
        for (int i = 0; i < gameManager.toolCards.size(); i++) {
            String nomeToolCard = gameManager.toolCards.get(i).name;

            Image image = new Image("ToolC/" + nomeToolCard + ".png");

            listToolCard.get(i).setImage(image);
        }


    }

    /**
     Loads the images associated with the relative PublicObjective Cards
     */
    private void loadPublicOC(GameManagerT gameManager) {
        for (int i = 0; i < gameManager.publicOCs.size(); i++) {
            String namePublicOC = gameManager.publicOCs.get(i);

            Image image = new Image("PublicOC/" + namePublicOC + ".png");

            listPublicOC.get(i).setImage(image);


        }

    }

    /**
     Loads the images associated with the relative Private Objective Cards
     */
    private void loadPrivateOC(GameManagerT gameManager) {
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        char namePrivateOC = playersLocal.get(gameManager.pos).privateO;
        Image image = new Image(namePrivateOC + ".png");
        privateOC.setImage(image);
    }

    /**
     Shows the number of tokens at the bottom left of the view
     */
    private void loadTokens(GameManagerT gameManager) {
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        String numeroTokens = playersLocal.get(gameManager.pos).tokens.toString();
        numTokens.setText(numeroTokens);


    }

    /**
     Loads the images associated with the dices contained in the pool draft
     */
    private void loadPoolDice(GameManagerT gameManager) {
        int numDadi = gameManager.pool.size();
        for (int i = 0; i < numDadi; i++) {

            if (gameManager.pool.get(i) != null) {
                int numero = gameManager.pool.get(i).getValue();
                char color = gameManager.pool.get(i).getColor();

                listDice.get(i).setStyle("-fx-background-image: url('Dices/" + numero + "" + color + sizeBackground);
            } else {
                listDice.get(i).setStyle(transparentBackround);

            }

        }
        for (int i = numDadi; i < listDice.size(); i++) {
            listDice.get(i).setStyle(transparentBackround);

        }


    }

    /**
     Loads the images associated with the dices placed in the relative MapCard
     */
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

                        myGrid.get(z).setStyle(("-fx-background-image: url('Dices/" + mynumber + "" + mycolor + sizeBackground));
                        myGrid.get(z).setOpacity(100);

                    } else {
                        myGrid.get(z).setStyle((transparentBackround));

                    }


                    z++;

                }

            }
            counterPosition++;

        }
    }


    /**
    Loads the dices contained in the roundtrack. The roundtrack is showed as an array of 10 comboboxes, each one contains the dices left-over from the single turn.
     */
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

    /**
     Creates a new {@link Position}, it's used in {@link StartGameController#onClickMap(ActionEvent)}
      */
    private void addPosition(int row, int column) {
        if (counterPositionGame < 4) {
            positions[counterPositionGame] = new Position(row, column);
            counterPositionGame++;
        } else {
            Logger.log("Over limit of positions!");

        }

    }
    /**
     Method called whenever the uses closes the game. Calls {@link ProxyClient#exitGame2()} which manages the disconnection
     */
    public void shutdown() {
        proxyClient.exitGame2();
        Platform.exit();
    }



    //INIT METHODS
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
            listDice.get(i).setStyle(transparentBackround);
        }
    }

    private void clearPosizioni() {
        for (int i = 0; i < positions.length; i++) {
            positions[i] = null;
        }
    }
    //END INIT METHODS


    //FXML METHODS
    @FXML
    private void endTurn(ActionEvent event) throws IOException {
        proxyClient.endTurn();
    }

    /**
     Returns the position of the selected cell in the MapCard. The cell is selected by clicking on a cell of the mapcard, which contains a {@code Button}
     */
    @FXML
    private void onClickMap(ActionEvent e) {
        Node source = (Node) e.getSource();
        colIndex = paneCarta0.getColumnIndex(source);
        rowIndex = paneCarta0.getRowIndex(source);
        addPosition(rowIndex, colIndex);
    }

    /**
    Return the index of the selected dice in the PoolDice. The dice is selected by clickin on the dice in the Pool which contains a {@code Button}
     */
    @FXML
    private void setSelectedDice(ActionEvent event) {
        Node selectedDice = (Node) event.getSource();
        positionPoolDice = listDice.indexOf(selectedDice);
    }

    /**
    Place the selected dice (selected via {@link StartGameController#setSelectedDice(ActionEvent)} in the selected cell of the mapcard
     (selected via {@link StartGameController#onClickMap(ActionEvent)}. Calls {@link ProxyClient#placeDice(Integer, Position)} to report the intent of placing a dice to the model.
     If the placement is not valid, creates a new {@link CustomAlert} showing an error message.

     The method is triggered by clickin on the {@code Place Dice} button
     */
    @FXML
    private void placeDice(ActionEvent event) {

        Position diceGridPosition = new Position();
        diceGridPosition.setRow(rowIndex);
        diceGridPosition.setColumn(colIndex);

        Boolean placed = proxyClient.placeDice(positionPoolDice, diceGridPosition);
        if(placed==false){
            new CustomAlert(Alert.AlertType.ERROR, "Error placing dice!" , "Unauthorized placement of dice!");

        }
        else proxyClient.updateViewFromC();
        clearPosizioni();

    }

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


    /**
    Shows or hide the rountrack made of {@code ComboBoxes}. It's triggered by the {@code Show RoundTrack} button
     */
    @FXML
    private void showRoundTrack(ActionEvent event) {
        if (roundTrack.isVisible() == true) {
            roundTrack.setVisible(false);
        } else
            roundTrack.setVisible(true);

    }

    /**
    Returns the selected dice from the RoundTrack
     */
    @FXML
    private void selectedRoundDice(ActionEvent event) {
        int comboboxselected = listaComboBox.indexOf(event.getSource());
        int indextest = listaComboBox.get(comboboxselected).getSelectionModel().getSelectedIndex();
        positionDiceRoundTrack.setColumn(comboboxselected);
        positionDiceRoundTrack.setHeight(indextest);
    }

    /**
    Returns the selected ToolCard. It's selected by clickin on the image of the toolcard in the view
     */
    @FXML
    private void selectToolCard(MouseEvent event) {
        indexofToolCard = listToolCard.indexOf(event.getSource());
    }

    /**
     Calls {@link ProxyClient#useToolC(Integer, Position, Position, Position, Position, PositionR, Integer, Integer)} which allows the user to use the selected ToolCard.
     If the use of the toolcard is not legit, creates a {@link CustomAlert} showing the error.
     */
    @FXML
    private void useToolCard(ActionEvent event) {
        Boolean usedToolC = proxyClient.useToolC(indexofToolCard, positions[0], positions[1], positions[2], positions[3], positionDiceRoundTrack, positionPoolDice, incrementValue);
        if(usedToolC==false){
            new CustomAlert(Alert.AlertType.ERROR, "Error using ToolCard!" , "Toolcard can't be used! Wrong parameters or violating rules!");

        }
        else proxyClient.updateViewFromC();

    }

    /**
     * Saves the value of increment used by some toolcards.
     */
    @FXML
    private void sendDataValue(ActionEvent event) {

        incrementValue = Integer.parseInt(changeValueField.getText());

    }

    //END OF FXML METHODS

    // METHODS CALLED FROM PROXYCLIENT

    /**
     * Enables the current user to actually play.
     */
    public void enable() {
        for (int i = 0; i < listDice.size(); i++) {
            listDice.get(i).setDisable(false);

        }
        placeDice.setDisable(false);
        fineTurno.setDisable(false);
        useToolCard.setDisable(false);

    }


    /**
     * Disables the user if it's not his turn
     */
    public void shut() {
        for (int i = 0; i < listDice.size(); i++) {
            listDice.get(i).setDisable(true);

        }
        placeDice.setDisable(true);
        fineTurno.setDisable(true);
        useToolCard.setDisable(true);

    }


    /**
     * Called by {@link ProxyClient#printScore(ArrayList, ArrayList, ArrayList)} when the game ends. It loads the end game view, managed by {@link WinnerController}.
     * If scores==Null, then it means that the game has ended while the user has been disconnected,
     * so on the winning user (the only that remains in the game) {@link StartGameController#aPrioriWin()} is called, while the reconnected one will just see a scoreboard
     * with the winner in the first position, without scores (because the game has ended because of disconnections)
     */
    public void printScore(ArrayList<String> nicks, ArrayList<Integer> scores) {

        Platform.runLater(
                () -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Winner.fxml"));
                    WinnerController winnerController = new WinnerController(nicks,scores);
                    loader.setController(winnerController);


                    try{

                        Parent root = loader.load();
                        Scene startedGame = new Scene(root, 1280, 800, Color.WHITE);
                        Stage window = (Stage) paneCarta0.getScene().getWindow();
                        window.setScene(startedGame);
                        window.show();
                    }
                    catch (IOException Exception) {
                        Logger.log("View not found. Error while loading");

                    }
                }
        );




    }

    //END OF METHODS CALLED FROM PROXYCLIENT



    /**
     * Method called by {@link ProxyClient#aPrioriWin()} when only a user remains in the game.
     */
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
                        Logger.log("View not found. Error while loading");

                    }
                }
        );

    }
    public void onTimeStatus(String s1, String s2){
        Platform.runLater(
                () -> {
                    if(s2==null) {
                        new CustomAlert(Alert.AlertType.INFORMATION, "Connected players", "Connected: " + s1 + "\n" );
                    }
                    else {
                        new CustomAlert(Alert.AlertType.INFORMATION, "Disconnected players", "Disconnected: " + s2);
                    }
                });
    }
}
