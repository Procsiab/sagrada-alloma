package client.gui;

import client.MainClient;
import client.ProxyClient;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
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
    private int posizionePoolDice, indexofToolCard;
    private Integer colIndex;
    private Integer rowIndex;
    private int incrementValue;
    private List<GridPane> listMapCard = new ArrayList<>();
    private ArrayList<Button> listDice = new ArrayList<>();
    private ArrayList<ImageView> listToolCard = new ArrayList<>();
    private ArrayList<ComboBox> listaComboBox = new ArrayList<>();
    private ArrayList<ImageView> listPublicOC = new ArrayList<>();

    private PositionR posizioneDadoRoundTrack = new PositionR();
    private Position posizioni[];
    private int counterPosizione = 0;


    public StartGameController() {
        MainClient.startGameController = this;
    }


    public void initialize(URL location, ResourceBundle resources) {
        loadBackground();
        // backGroundTransition();
        posizioni = new Position[4];
        initMapCards();
        loadDadi();
        initToolCards();
        loadComboBox();
        initPublicOC();
        shut();
        loadPool();
        System.out.print("INIZIALIZZAZIONE COMPLETATA");

    }


    public void updateView(GameManagerT gameManager) {
        Platform.runLater(
                () -> {
                    System.out.print("I was updated, receiving the GameManager object:\n" + gameManager.toString());

                    // Useful variables
                    clearPosizioni();
                    counterPosizione = 0;
                    String numeroTokens;
                    int numDadi;
                    ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
                    int counterPosition = gameManager.pos;

                    System.out.println("Valore counterPosition:" + counterPosition);
                    System.out.println("Valore gameManager:" + gameManager.pos);
                    //Loading mapCards into view
                    addMapCards(gameManager);

                    // GET TOKENS
                    loadTokens(gameManager);

                    // LOAD POOL
                    loadPoolDice(gameManager);

                    // LOAD TOOLCARDS
                    loadToolCards(gameManager);

                    // LOAD PUBLIC OC
                    loadPublicOC(gameManager);

                    // LOAD PRIVATE OC
                    loadPrivateOC(gameManager);

                    // LOAD DICES INTO MAPS
                    loadDiceMaps(gameManager);

                    //LOAD DICES INTO ROUNDTRACK
                    loadDiceRoundTrack(gameManager);

                });
    }

    private void loadDiceRoundTrack(GameManagerT gameManager) {
        //Arraylist made of arraylist containing the data I need
        ArrayList<ArrayList<Dice>> roundTrackData = gameManager.roundTrack.getDices();
        System.out.println(roundTrackData);
        final Map<String, WeakReference<Image>> cache = new HashMap<>();

        for (int h = 0; h < roundTrackData.size(); h++) {
            ComboBox<String> combo = listaComboBox.get(h);
            combo.setCellFactory(c -> new StatusListCell(cache));
            ObservableList<String> options = FXCollections.observableArrayList();

            List<Dice> testing = roundTrackData.get(h);
            System.out.println(" Testing:" + testing);
            System.out.println(" Testing size:" + testing.size());

            for (Dice die : testing) {
                System.out.println(" Inside cicle ");
                String color = Character.toString(die.getColor());
                String value = die.getValue().toString();
                String diceRound = value + color + ".png";

                options.add(diceRound);
                //listaComboBox is an array list containing 10 comboboxes
                System.out.println("Dice color " + color);
                System.out.println("Dice value" + value);
            }

            combo.setItems(options);
        }
    }

    private void loadDiceMaps(GameManagerT gameManager) {
        int counterPosition = gameManager.pos;
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;



        System.out.println("Valore counterPosition:" + counterPosition);
        System.out.println("Valore gameManager:" + gameManager.pos);


        for (int i = 0; i < playersLocal.size(); i++) {
            System.out.println("Valore counterPosition dentro al ciclo:" + counterPosition);
            System.out.println("Valore gameManager dentro al ciclo:" + gameManager.pos);

            if (counterPosition > playersLocal.size() - 1)
                counterPosition = 0;
            Dice[][] myOverlay = playersLocal.get(counterPosition).overlay.getDicePositions();

            ObservableList<Node> myGrid = listMapCard.get(i).getChildren();
            int z = 0;
            for (int k = 0; k < 4; k++) {
                for (int y = 0; y < 5; y++) {

                    System.out.println("NEL CICLO, PRIMA DELL'IF");

                    if (myOverlay[k][y] != null) {
                        System.out.println("APPENA ENTRATO NELL'IF ");
                        char mycolor = myOverlay[k][y].getColor();
                        System.out.println("Stampo Colore" + mycolor);
                        int mynumber = myOverlay[k][y].getValue();
                        System.out.println("Stampo Numero" + mynumber);

                        myGrid.get(z).setStyle(("-fx-background-image: url('" + mynumber + "" + mycolor + ".png');-fx-background-size: 100% 100%;"));
                        myGrid.get(z).setOpacity(100);
                        System.out.println("ASSEGNAZIONE DADO FATTA ");

                    } else {
                        myGrid.get(z).setStyle(("-fx-background-color: transparent;-fx-background-size: 100% 100%;"));

                    }
                    System.out.println("FUORI IF ");


                    z++;
                    System.out.println(z);

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
            System.out.println("NOME PublicOC:" + namePublicOC);

            Image image = new Image(namePublicOC + ".png");
            System.out.println("CARICAMENTO PublicOC");

            listPublicOC.get(i).setImage(image);


        }

    }

    private void loadToolCards(GameManagerT gameManager) {
        for (int i = 0; i < gameManager.toolCards.size(); i++) {
            String nomeToolCard = gameManager.toolCards.get(i).name;
            System.out.println("NOME TOOCARD:" + nomeToolCard);

            Image image = new Image(nomeToolCard + ".png");
            System.out.println("CARICAMENTO TOOLCARD");

            listToolCard.get(i).setImage(image);
        }


    }

    private void addMapCards(GameManagerT gameManager) {
        ArrayList<PlayerT> playersLocal = gameManager.vPlayers;
        int counterPosition = gameManager.pos;
        for (int i = 0; i < playersLocal.size(); i++) {
            String nomeCarta;
            System.out.println("Valore counterPosition dentro al ciclo:" + counterPosition);
            System.out.println("Valore gameManager dentro al ciclo:" + gameManager.pos);

            if (counterPosition > playersLocal.size() - 1)
                counterPosition = 0;
            System.out.println("Valore counterPosition dentro al ciclo dopo reset :" + counterPosition);
            System.out.println(playersLocal.get(counterPosition).window.getName());
            nomeCarta = playersLocal.get(counterPosition).window.getName();

            listMapCard.get(i).setStyle("-fx-background-image: url('" + nomeCarta + ".png');-fx-background-size: 100% 100%;");
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
        System.out.println("Numero di dadi :" + numDadi);
        for (int i = 0; i < numDadi; i++) {

            System.out.println("Valore di i nel ciclo:" + i);
            // INSERIRE EFFETIVO VALORE DEL DADO
            if (gameManager.pool.get(i) != null) {
                int numero = gameManager.pool.get(i).getValue();
                char color = gameManager.pool.get(i).getColor();
                System.out.println("Numero :" + numero + "\n");
                System.out.println("Colore :" + color + "\n");
                listDice.get(i).setStyle("-fx-background-image: url('" + numero + "" + color + ".png');-fx-background-size: 100% 100%;");
            } else {
                listDice.get(i).setStyle("-fx-background-color: transparent;-fx-background-size: 100% 100%;");

            }

        }
        for (int i = numDadi; i < listDice.size(); i++) {
            listDice.get(i).setStyle("-fx-background-color: transparent;-fx-background-size: 100% 100%;");

        }


    }


    private void aggiungiPosizione(int row, int column) {
        if (counterPosizione < 4) {
            posizioni[counterPosizione] = new Position(row, column);
            counterPosizione++;
        } else {
            System.out.println("Superato limite posizioni!");

        }

    }

    public void shutdown() {
        // cleanup code here...
        System.out.println("CHIUSURA FINESTRA");
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

    private void loadDadi() {
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

    private void loadComboBox() {
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

    private void loadPool() {
        for (int i = 0; i < listDice.size(); i++) {
            listDice.get(i).setStyle("-fx-background-color: transparent;-fx-background-size: 100% 100%;");
        }
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


    private void clearPosizioni() {
        for (int i = 0; i < posizioni.length; i++) {
            posizioni[i] = null;
        }
    }


    //FXML METHODS
    @FXML
    private void fineTurno(ActionEvent event) throws IOException {
        System.out.print("\"Turno Finito\"");
        proxyClient.endTurn();

    }

    @FXML
    private void onClickMap(ActionEvent e) {
        System.out.println("MouseEntered");
        Node source = (Node) e.getSource();
        System.out.println(source);

        colIndex = paneCarta0.getColumnIndex(source);
        rowIndex = paneCarta0.getRowIndex(source);
        aggiungiPosizione(rowIndex, colIndex);
        System.out.println(colIndex);
        System.out.println(rowIndex);
    }

    @FXML
    private void setSelectedDice(ActionEvent event) {
        Node selectedDice = (Node) event.getSource();
        String nomeDado = selectedDice.getId();
        System.out.println(nomeDado);

        posizionePoolDice = listDice.indexOf(selectedDice);
        System.out.println(posizionePoolDice);
    }

    @FXML
    private void placeDice(ActionEvent event) {
        System.out.print("\"Entrata Dado \"");

        Position diceGridPosition = new Position();
        diceGridPosition.setRow(rowIndex);
        diceGridPosition.setColumn(colIndex);

        proxyClient.placeDice(posizionePoolDice, diceGridPosition);
        clearPosizioni();
        System.out.print("\"Dado Posizionato\"");


    }

    // CHANGE TO ABSOLUTE VALUES THE TRANSITION, TO AVOID BUGS WHEN THE USER KEEPS MOVING AROUND OVER THE CARDS
    @FXML
    private void zoomToolCard(MouseEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.millis(2000), (Node) event.getSource());
        st.setToX(3.5f);
        st.setToY(3.5f);
        st.setCycleCount(1);
        st.setAutoReverse(true);

        ((Node) event.getSource()).toFront();
        st.play();
    }

    @FXML
    private void zoomOutToolCard(MouseEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.millis(2000), (Node) event.getSource());
        st.setToX(1f);
        st.setToY(1f);
        st.setCycleCount(1);
        st.setAutoReverse(true);
        st.play();
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
        System.out.print("\"Dado Selezionato dal round dice!\"");
        int comboboxselected = listaComboBox.indexOf(event.getSource());
        System.out.print("Combobox selezionato:" + comboboxselected);
        int indextest = listaComboBox.get(comboboxselected).getSelectionModel().getSelectedIndex();
        System.out.print("Item all'interno del combobox:" + indextest);
        posizioneDadoRoundTrack.setColumn(comboboxselected);
        posizioneDadoRoundTrack.setHeight(indextest);

        System.out.print("Colonna in positionR:" + posizioneDadoRoundTrack.getColumn());
        System.out.print("Altezza colonna in positionR:" + posizioneDadoRoundTrack.getHeight());

    }

    @FXML
    private void selectToolCard(MouseEvent event) {
        indexofToolCard = listToolCard.indexOf(event.getSource());
        System.out.print("Posizione toolCard" + indexofToolCard);
    }

    @FXML
    private void useToolCard(ActionEvent event) {
        System.out.print("\"Using toolCard!\"");
        //i1 è la posizione della toolcard selezionata
        //p1,p2,p3,p4 sono le posizioni nella griglia mappa. Le dispari sono la posizione finale, le pari la finale
        //pr è la posizione nel roundtrack, già inserita
        //i2 è la posizione del dado nel pool
        //i3 è il cambio valore o incremento
        System.out.print("Valore di indexToolCard:" + indexofToolCard + "\n");
        for (int i = 0; i < posizioni.length; i++) {
            if (posizioni[i] != null) {
                System.out.print("Valore di posizioni[" + i + "]:" + posizioni[i].getRow() + posizioni[i].getColumn() + "\n");
            } else
                System.out.print("Valore di posizioni[" + i + "]:NULL\n");


        }

        System.out.print("Valore di posizioneDadoRoundTrack:" + posizioneDadoRoundTrack + "\n");
        System.out.print("Valore di posizionePoolDice:" + posizionePoolDice + "\n");
        System.out.print("Valore di incrementvalue:" + incrementValue + "\n");


        proxyClient.useToolC(indexofToolCard, posizioni[0], posizioni[1], posizioni[2], posizioni[3], posizioneDadoRoundTrack, posizionePoolDice, incrementValue);


    }

    @FXML
    private void sendDataValue(ActionEvent event) {

        incrementValue = Integer.parseInt(changeValueField.getText());
        System.out.print("Valore di incremento:" + incrementValue);

    }

    //END OF FXML METHODS

    //TODO Implement the following methods
    // METHODS CALLED FROM MIDDLEWARE


    public void setWinner() {
        //start animation for the winner
        System.out.println("HAI VINTO FROCETTO");
        Platform.runLater(
                () -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Winner.fxml"));
                    try {
                        Parent root1 = loader.load();
                        Scene startedGame = new Scene(root1, 1280, 800, Color.WHITE);
                        Stage window = (Stage) paneCarta0.getScene().getWindow();
                        window.setScene(startedGame);
                        window.show();
                    } catch (IOException Exception) {
                        System.out.println("View not found. Error while loading");

                    }
                });
    }

    public void enable() {
        for (int i = 0; i < listDice.size(); i++) {
            listDice.get(i).setDisable(false);

        }
        placeDice.setDisable(false);
        fineTurno.setDisable(false);
        useToolCard.setDisable(false);

    }


    public void printScore(Integer score) {

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

    }
}
